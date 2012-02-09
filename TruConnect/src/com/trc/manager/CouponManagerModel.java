package com.trc.manager;

import java.util.Collection;

import com.trc.coupon.Coupon;
import com.trc.coupon.UserCoupon;
import com.trc.exception.management.CouponManagementException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.ServiceInstance;

public interface CouponManagerModel {

  public Collection<UserCoupon> getUserCoupons(int userId) throws CouponManagementException;

  public int applyCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
      throws CouponManagementException;

  public void cancelCoupon(Coupon coupon, User user, Account account, ServiceInstance serviceInstance)
      throws CouponManagementException;

}
