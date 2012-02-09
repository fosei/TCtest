package com.trc.manager.webflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.coupon.Coupon;
import com.trc.exception.WebFlowException;
import com.trc.exception.management.CouponManagementException;
import com.trc.manager.CouponManager;
import com.trc.user.User;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

@Component
public class CouponFlowManager {
  @Autowired
  private CouponManager couponManager;

  private static String ERROR_APPLY_COUPON = "An error occurred while applying your coupon. No changes were made, please try again.";
  private static String ERROR_FETCH_COUPON = "An error occured while fetching your coupon. No changes were made, please try again.";

  public int applyCoupon(Coupon coupon, User user, Account account, String mdn) throws WebFlowException {
    try {
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalid(mdn);
      return couponManager.applyCoupon(coupon, user, account, serviceInstance);
    } catch (CouponManagementException e) {
      WebFlowUtil.addError(ERROR_APPLY_COUPON);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public int applyCouponPayment(Coupon coupon, User user, Account account) throws WebFlowException {
    try {
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalid("");
      return couponManager.applyCoupon(coupon, user, account, serviceInstance);
    } catch (CouponManagementException e) {
      WebFlowUtil.addError(ERROR_APPLY_COUPON);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public Coupon getCouponByCode(Coupon coupon) throws WebFlowException {
    try {
      Coupon loadedCoupon = couponManager.getCouponByCode(coupon.getCouponCode());
      return loadedCoupon;
    } catch (CouponManagementException e) {
      WebFlowUtil.addError(ERROR_FETCH_COUPON);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public boolean hasCoupon(Coupon coupon) throws WebFlowException {
    return coupon != null && !coupon.isEmpty();
  }

  public boolean isCouponPayment(Coupon coupon) throws WebFlowException {
    return coupon != null && coupon.isPayment();
  }

  public void setCoupon(Coupon coupon, Coupon fetchedCoupon) {
    if (coupon != null && fetchedCoupon != null) {
      coupon.setCouponDetail(fetchedCoupon.getCouponDetail());
      coupon.setCouponId(fetchedCoupon.getCouponId());
      coupon.setEnabled(fetchedCoupon.isEnabled());
      coupon.setEndDate(fetchedCoupon.getEndDate());
      coupon.setQuantity(fetchedCoupon.getQuantity());
      coupon.setStartDate(fetchedCoupon.getStartDate());
      coupon.setUsed(fetchedCoupon.getUsed());
    }
  }

}