package com.trc.dao;

import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

public interface ActivationStateDaoModel {

  public int saveRegistrationMap(ActivationMap registrationMap);

  public void updateRegistrationMap(ActivationMap registrationMap);

  public ActivationMap getRegistrationMap(int registrationId);

  public void saveRegistrationState(ActivationState registrationState);

  public void updateRegistratonState(ActivationState registrationState);

  public ActivationState getRegistrationState(ActivationStateId registrationStateId);

}
