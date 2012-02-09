package com.trc.user.activation;

import java.io.Serializable;

import com.trc.coupon.Coupon;
import com.tscp.mvne.CreditCard;

//TODO move this class to the payment package
public class CreditCardPayment implements Serializable {
  private static final long serialVersionUID = -3073325472062300186L;
  private CreditCard creditCard = new CreditCard();
  private Coupon coupon = new Coupon();

  public CreditCard getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(CreditCard creditCard) {
    this.creditCard = creditCard;
  }

  public Coupon getCoupon() {
    return coupon;
  }

  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }

}