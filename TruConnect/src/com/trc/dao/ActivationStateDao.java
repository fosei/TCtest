package com.trc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.util.logger.activation.ActState;
import com.trc.util.logger.activation.ActivationMap;
import com.trc.util.logger.activation.ActivationState;
import com.trc.util.logger.activation.ActivationStateId;

@Repository
public class ActivationStateDao extends HibernateDaoSupport implements ActivationStateDaoModel {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public int saveRegistrationMap(ActivationMap activationMap) {
    return (Integer) getHibernateTemplate().save(activationMap);
  }

  @Override
  public void updateRegistrationMap(ActivationMap activationMap) {
    getHibernateTemplate().update(activationMap);
  }

  @Override
  public void saveRegistrationState(ActivationState activationState) {
    getHibernateTemplate().save(activationState);
  }

  @Override
  public void updateRegistratonState(ActivationState activationState) {
    getHibernateTemplate().update(activationState);
  }

  @Override
  public ActivationState getRegistrationState(ActivationStateId activationStateId) {
    return getHibernateTemplate().get(ActivationState.class, activationStateId);
  }

  @Override
  public ActivationMap getRegistrationMap(int activationId) {
    return getHibernateTemplate().get(ActivationMap.class, activationId);
  }

  public ActivationState getRegistrationState(ActivationMap activationMap, ActState actState) {
    for (ActivationState rState : activationMap.getStates()) {
      if (rState.getState().equals(actState)) {
        return rState;
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<ActivationMap> getRegistrationMapByUserId(int userId) {
    return getHibernateTemplate().find("from ActivationMap activationMap where activationMap.user.userId = ?", userId);
  }
}
