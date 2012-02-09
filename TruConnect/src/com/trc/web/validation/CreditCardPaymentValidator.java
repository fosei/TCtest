package com.trc.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.coupon.Coupon;
import com.trc.user.activation.CreditCardPayment;
import com.tscp.mvne.CreditCard;

@Component
public class CreditCardPaymentValidator implements Validator {
  @Autowired
  private CreditCardValidator creditCardValidtor;
  @Autowired
  private CouponValidator couponValidator;

  @Override
  public boolean supports(Class<?> myClass) {
    return CreditCardPayment.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreditCardPayment creditCardPayment = (CreditCardPayment) target;
    checkCoupon(creditCardPayment.getCoupon(), errors);
    checkCreditCard(creditCardPayment.getCreditCard(), errors);
  }

  private void checkCreditCard(CreditCard creditCard, Errors errors) {
    errors.pushNestedPath("creditCard");
    creditCardValidtor.validate(creditCard, errors);
    errors.popNestedPath();
  }

  private void checkCoupon(Coupon coupon, Errors errors) {
    if (coupon != null && !coupon.isEmpty()) {
      errors.pushNestedPath("coupon");
      couponValidator.validate(coupon, errors);
      errors.popNestedPath();
    }
  }
}