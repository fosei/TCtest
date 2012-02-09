package com.trc.util.logger.activation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.trc.coupon.Coupon;
import com.trc.dao.ActivationStateDao;
import com.trc.user.User;

@Service
@Scope("session")
public class ActivationLogger {
  @Autowired
  private ActivationStateDao activationStateDao;
  private ActivationMap activationMap = null;
  private ActivationState previousState = null;

  public void startLogging(User user) {
    setActivationMap(new ActivationMap(user));
    activationStateDao.saveRegistrationMap(getActivationMap());
    setPreviousState(new ActivationState(ActState.ROOT, getActivationMap()));
    activationStateDao.saveRegistrationState(getPreviousState());
  }

  public void finishLogging() {
    getPreviousState().setDateOut(new Date());
    activationStateDao.updateRegistratonState(getPreviousState());
  }

  public ActivationState logState(ActState state) {
    ActivationState registrationState = new ActivationState(state, getActivationMap());
    registrationState.setParentState(getPreviousState());
    if (getPreviousState() != null) {
      getPreviousState().getChildren().add(registrationState);
      getPreviousState().setDateOut(new Date());
      activationStateDao.updateRegistratonState(getPreviousState());
    }
    setPreviousState(registrationState);
    return registrationState;
  }

  /* **************************************************************
   * DAO Access methods
   * ***************************************************************
   */

  public ActivationMap getActivationMap(int actId) {
    return activationStateDao.getRegistrationMap(actId);
  }

  public ActivationState getActivationState(ActivationMap activationMap, ActState actState) {
    return activationStateDao.getRegistrationState(activationMap, actState);
  }

  /* **************************************************************
   * State logging for webflow
   * ***************************************************************
   */
  public void logReserveProfile() {
    logState(ActState.RESERVE_PROFILE);
  }

  public void logContactInfo() {
    logState(ActState.INFO_CONTACT);
  }

  public void logDeviceInfo() {
    logState(ActState.INFO_DEVICE);
  }

  public void logPaymentInfo() {
    logState(ActState.INFO_PAYMENT);
  }

  public void logTestActivation() {
    logState(ActState.TEST_ACTIVATION);
  }

  public void logCreateBilling() {
    logState(ActState.ACTIVATE_BILLING_ACCOUNT);
  }

  public void logPayment() {
    logState(ActState.ACTIVATE_PAYMENT);
  }

  public void logActivation() {
    logState(ActState.ACTIVATE_NETWORK_SERVICE);
  }

  public void logCreateService() {
    logState(ActState.ACTIVATE_CREATE_SERVICE);
  }

  public void logEnableUser() {
    logState(ActState.ACTIVATE_ENABLE_USER);
  }

  public void logSaveUser() {
    logState(ActState.ACTIVATE_SAVE_USER);
  }

  public void logWelcome() {
    logState(ActState.NOTIFY_WELCOME);
  }

  public void logHasCoupon(Coupon coupon) {
    logState(ActState.HAS_COUPON);
  }

  public void logIsCouponPayment() {
    logState(ActState.IS_COUPON_PAYMENT);
  }

  public void logApplyCouponPayment() {
    logState(ActState.APPLY_COUPON_PAYMENT);
  }

  public void logCheckCouponContract() {
    logState(ActState.CHECK_COUPON_CONTRACT);
  }

  public void logApplyContract() {
    logState(ActState.APPLY_CONTRACT);
  }

  /* **************************************************************
   * Getters / Setters
   * ***************************************************************
   */

  public ActivationMap getActivationMap() {
    return activationMap;
  }

  public void setActivationMap(ActivationMap activationMap) {
    this.activationMap = activationMap;
  }

  public ActivationState getPreviousState() {
    return previousState;
  }

  public void setPreviousState(ActivationState previousState) {
    this.previousState = previousState;
  }

}
