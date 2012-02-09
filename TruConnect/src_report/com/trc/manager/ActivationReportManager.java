package com.trc.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.report.ActivationReport;
import com.trc.report.UserActivationReport;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;

@Component
public class ActivationReportManager {
  @Autowired
  private UserManager userManager;
  @Autowired
  private ActivationStateManager activationStateManager;
  private static final String RESERVED_USER = "reserve";
  private static final int RESERVED_SUBSTRING_LENGTH = 16;

  public ActivationReport getActivationReport(DateTime startDate, DateTime endDate) {
    DevLogger.log("Fetching activation report for range " + startDate + " to " + endDate);
    ActivationReport report = new ActivationReport();
    report.setActivatedUsers(getActiveUsers(startDate, endDate));
    report.setUniqueReservations(getUniqueReservedUsers(startDate, endDate));
    report.setFailedStates(getFailedStates(report.getFailedReservations()));
    return report;
  }

  public UserActivationReport getUserActivationReport(int userId) {
    User user = userManager.getUserById(userId);

    ActivationState rootState = getFirstState(user.getUserId());
    List<ActivationState> activationStates = new ArrayList<ActivationState>();
    if (rootState != null) {
      activationStates.add(rootState);
      while (rootState.getChildren().size() > 0) {
        rootState = rootState.getChildren().toArray(new ActivationState[0])[0];
        activationStates.add(rootState);
      }
    }

    UserActivationReport userActivationReport = new UserActivationReport();
    userActivationReport.setUser(user);
    userActivationReport.setActivationStates(activationStates);
    return userActivationReport;
  }

  public Collection<User> getUniqueReservedUsers() {
    return getUniqueReservedUsers(null, null);
  }

  public Collection<User> getUniqueReservedUsers(DateTime startDate, DateTime endDate) {
    setDates(startDate, endDate);
    List<User> results = userManager.searchByEmailAndDate(RESERVED_USER, startDate, endDate);
    for (User user : results) {
      user.setEmail(user.getEmail().substring(RESERVED_SUBSTRING_LENGTH));
      user.setUsername(user.getUsername().substring(RESERVED_SUBSTRING_LENGTH));
    }
    Collection<User> uniqueResults = new HashSet<User>();
    uniqueResults.addAll(results);
    return uniqueResults;
  }

  public Collection<User> getActiveUsers() {
    return getActiveUsers(null, null);
  }

  public Collection<User> getActiveUsers(DateTime startDate, DateTime endDate) {
    setDates(startDate, endDate);
    return userManager.searchByNotEmailAndDate(RESERVED_USER, startDate, endDate);
  }

  public ActivationState getFirstState(int userId) {
    List<ActivationMap> activations = activationStateManager.getActivationMapByUserId(userId);
    if (activations != null && activations.size() > 0) {
      ActivationMap actMap = activations.get(0);
      ActivationState actState = activationStateManager.getActivationState(actMap, ActState.ROOT);
      return actState;
    }
    return null;
  }

  public ActivationState getLastState(int userId) {
    List<ActivationMap> activations = activationStateManager.getActivationMapByUserId(userId);
    if (activations != null && activations.size() > 0) {
      ActivationMap actMap = activations.get(0);
      ActivationState actState = activationStateManager.getActivationState(actMap, ActState.ROOT);
      while (actState.getChildren().size() > 0) {
        actState = actState.getChildren().toArray(new ActivationState[0])[0];
      }
      return actState;
    }
    return null;
  }

  private EnumMap<ActState, Integer> getFailedStates(Collection<User> failedReservations) {
    EnumMap<ActState, Integer> failedStates = new EnumMap<ActState, Integer>(ActState.class);
    int count;
    ActState lastState;
    for (User user : failedReservations) {
      ActivationState lastActState = getLastState(user.getUserId());
      if (lastActState != null) {
        lastState = lastActState.getState();
        DevLogger.log("User: " + user.getUsername() + " failed in state: " + lastState);
        DevLogger.log("... failedStates entry: " + lastState + ", " + failedStates.get(lastState));
        count = failedStates.get(lastState) == null ? 0 : failedStates.get(lastState);
        DevLogger.log("... new count: " + count);
        failedStates.put(lastState, count + 1);
      } else {
        count = failedStates.get(ActState.ROOT) == null ? 0 : failedStates.get(ActState.ROOT);
        failedStates.put(ActState.ROOT, count + 1);
      }
    }
    return failedStates;
  }

  private void setDates(DateTime startDate, DateTime endDate) {
    if (startDate == null || endDate == null) {
      if (startDate == null) {
        startDate = new DateTime(2000, 1, 1, 0, 0);
      }
      if (endDate == null) {
        endDate = new DateTime();
      }
    }
  }

}
