package com.trc.report;

import java.util.ArrayList;
import java.util.List;

import com.trc.user.User;
import com.trc.util.logger.activation.ActivationState;

public class UserActivationReport {
  private User User;
  private List<ActivationState> activationStates = new ArrayList<ActivationState>();

  public User getUser() {
    return User;
  }

  public void setUser(User user) {
    User = user;
  }

  public List<ActivationState> getActivationStates() {
    return activationStates;
  }

  public void setActivationStates(List<ActivationState> activationStates) {
    this.activationStates = activationStates;
  }

}