package com.trc.report;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;

import com.trc.user.User;
import com.trc.util.logger.activation.ActState;

public class ActivationReport {
  private Collection<User> activatedUsers;
  private Collection<User> uniqueReservations;
  private Collection<User> failedReservations;
  private Collection<User> successfulReservations;
  private ActivationReportSummary activationReportSummary;
  private EnumMap<ActState, Integer> failedStates;

  public Collection<User> getActivatedUsers() {
    return activatedUsers;
  }

  public void setActivatedUsers(Collection<User> activatedUsers) {
    this.activatedUsers = activatedUsers;
  }

  public Collection<User> getUniqueReservations() {
    return uniqueReservations;
  }

  public void setUniqueReservations(Collection<User> reservedUsers) {
    this.uniqueReservations = reservedUsers;
  }

  public Collection<User> getFailedReservations() {
    if (failedReservations == null) {
      failedReservations = new HashSet<User>();
      failedReservations.addAll(getUniqueReservations());
      failedReservations.removeAll(getActivatedUsers());
    }
    return failedReservations;
  }

  public Collection<User> getSuccessfulReservations() {
    if (successfulReservations == null) {
      successfulReservations = new HashSet<User>();
      successfulReservations.addAll(getUniqueReservations());
      successfulReservations.removeAll(getFailedReservations());
    }
    return successfulReservations;
  }

  public EnumMap<ActState, Integer> getFailedStates() {
    return failedStates;
  }

  public void setFailedStates(EnumMap<ActState, Integer> failedStates) {
    this.failedStates = failedStates;
  }

  public ActivationReportSummary getSummary() {
    if (activationReportSummary == null) {
      activationReportSummary = new ActivationReportSummary();
      activationReportSummary.setActivated(getActivatedUsers().size());
      activationReportSummary.setUniqueReservations(getUniqueReservations().size());
      activationReportSummary.setFailedReservations(getFailedReservations().size());
      activationReportSummary.setSuccessfulReservations(getSuccessfulReservations().size());
    }
    return activationReportSummary;
  }

  public void reset() {
    activatedUsers = null;
    uniqueReservations = null;
    failedReservations = null;
    successfulReservations = null;
  }
}
