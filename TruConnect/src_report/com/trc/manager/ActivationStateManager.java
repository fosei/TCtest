package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.dao.ActivationStateDao;
import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

@Component
public class ActivationStateManager {
  @Autowired
  private ActivationStateDao activationStateDao;

  public ActivationMap getActivationMap(int actId) {
    return activationStateDao.getRegistrationMap(actId);
  }

  public List<ActivationMap> getActivationMapByUserId(int userId) {
    return activationStateDao.getRegistrationMapByUserId(userId);
  }

  public ActivationState getActivationState(ActivationMap actMap, ActState state) {
    ActivationStateId actStateId = new ActivationStateId();
    actStateId.setActivationMap(actMap);
    actStateId.setActState(state);
    return activationStateDao.getRegistrationState(actStateId);
  }

}
