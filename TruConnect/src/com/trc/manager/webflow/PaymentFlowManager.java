package com.trc.manager.webflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.GatewayException;
import com.trc.exception.WebFlowException;
import com.trc.exception.management.PaymentManagementException;
import com.trc.manager.PaymentManager;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.PaymentUnitResponse;

@Component
public class PaymentFlowManager {
  @Autowired
  private PaymentManager paymentManager;

  private static final String ERROR_MAKE_PAYMENT = "An error occured while making your payment. Your account has not been charged. Please try again.";
  private static final String ERROR_ADD_METHOD = "An error occurred while adding your payment method. Please try again.";
  private static final String ERROR_REMOVE_METHOD = "An error occurred while clearing your previous attempt's record. Your account has not been charged or saved. Please try again.";
  private static final String ERROR_RETRIEVE_METHOD = "An error occurred whiled retrieving your payment information. Please try again.";

  public PaymentUnitResponse makeActivationPayment(User user, Account account, CreditCard creditCard)
      throws WebFlowException {
    try {
      return paymentManager.makeActivationPayment(user, account, creditCard);
    } catch (PaymentManagementException e) {
      WebFlowUtil.addError(ERROR_MAKE_PAYMENT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public PaymentUnitResponse makePayment(User user, Account account, CreditCard creditCard, String amount)
      throws WebFlowException {
    try {
      return paymentManager.makePayment(user, account, creditCard, amount);
    } catch (PaymentManagementException e) {
      WebFlowUtil.addError(ERROR_MAKE_PAYMENT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public PaymentUnitResponse makePayment(User user, Account account, int paymentId, String amount)
      throws GatewayException {
    try {
      return paymentManager.makePayment(user, account, paymentId, amount);
    } catch (PaymentManagementException e) {
      WebFlowUtil.addError(ERROR_MAKE_PAYMENT);
      throw new GatewayException(e.getMessage(), e.getCause());
    }
  }

  public void bindCreditCard(CreditCard outCreditCard, CreditCard inCreditCard) {
    TruConnectUtil.copyCreditCard(outCreditCard, inCreditCard);
  }

  public void addPaymentMethod(User user, CreditCard creditCard) throws GatewayException {
    try {
      CreditCard createdCreditCard = paymentManager.addCreditCard(user, creditCard);
      TruConnectUtil.copyCreditCard(creditCard, createdCreditCard);
    } catch (PaymentManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_ADD_METHOD);
      throw new GatewayException(e.getMessage(), e.getCause());
    }
  }

  public void removePaymentMethod(User user, CreditCard creditCard) throws WebFlowException {
    try {
      paymentManager.removeCreditCard(user, creditCard);
    } catch (PaymentManagementException e) {
      WebFlowUtil.addError(ERROR_REMOVE_METHOD);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public List<CreditCard> getCreditCards(User user) throws WebFlowException {
    try {
      return paymentManager.getCreditCards(user);
    } catch (Exception e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_RETRIEVE_METHOD);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public CreditCard getCreditCardDetail(int paymentId) throws WebFlowException {
    try {
      return paymentManager.getCreditCard(paymentId);
    } catch (PaymentManagementException e) {
      WebFlowUtil.addError(ERROR_RETRIEVE_METHOD);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public boolean isExistingCard(CreditCard creditCard) {
    return creditCard.getCreditCardNumber().toLowerCase().contains("x");
  }
}
