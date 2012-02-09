package com.trc.util.logger.activation;

import java.util.Date;

import org.hibernate.Session;

import com.trc.hibernate.HibernateUtil_Activation;
import com.trc.util.ClassUtils;

public class ActStateTester {
  private static ActivationState prevState;
  private static ActivationMap regMap;

  public static void main(String... args) {
    Session session = HibernateUtil_Activation.beginTransaction();

    ActivationMap regMap = (ActivationMap) session.get(ActivationMap.class, 116);
    System.out.println("\n" + ClassUtils.toString(regMap));
    System.out.println("\n" + ClassUtils.toString(regMap.getUser()));
    // for (RegistrationState rState : regMap.getStates()) {
    // System.out.println("\n" + ClassUtils.toString(rState));
    // }
    System.out.println("\n" + ClassUtils.toString(regMap.getRootState()));
    System.out.println("\n" + ClassUtils.toString(regMap.getRootState().getChildren().iterator().next()));

    // RegistrationStateId regStateId = new RegistrationStateId();
    // regStateId.setRegistrationMap(regMap);
    // regStateId.setState(RegState.ROOT);
    // RegistrationState regState = (RegistrationState)
    // session.get(RegistrationState.class, regStateId);
    // System.out.println("\n" + ClassUtils.toString(regState));
    // System.out.println("\n" +
    // ClassUtils.toString(regState.getRegistrationMap()));
    // System.out.println("\n" +
    // ClassUtils.toString(regState.getRegistrationMap().getUser()));

    HibernateUtil_Activation.commitTransaction();
    HibernateUtil_Activation.closeSession();

  }

  public static ActivationState logState(ActState state, Session session) {
    ActivationState registrationState = new ActivationState(state, regMap);
    registrationState.setParentState(prevState);

    if (prevState != null) {
      prevState.getChildren().add(registrationState);
      prevState.setDateOut(new Date());
      session.update(prevState);
    }

    session.save(registrationState);
    prevState = registrationState;

    return registrationState;
  }
}
