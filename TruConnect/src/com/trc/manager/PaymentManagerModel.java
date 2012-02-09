package com.trc.manager;

import java.util.List;

import com.trc.exception.management.PaymentManagementException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.CustPmtMap;
import com.tscp.mvne.PaymentUnitResponse;

public interface PaymentManagerModel {

  public PaymentUnitResponse makePayment(User user, Account account, CreditCard creditCard, String amount)
      throws PaymentManagementException;

  public PaymentUnitResponse makePayment(User user, Account account, int paymentId, String amount)
      throws PaymentManagementException;

  public CreditCard getCreditCard(int paymentId) throws PaymentManagementException;

  public CreditCard addCreditCard(User user, CreditCard creditCard) throws PaymentManagementException;

  public List<CustPmtMap> removeCreditCard(User user, int paymentId) throws PaymentManagementException;

  public List<CustPmtMap> updateCreditCard(User user, CreditCard creditCard) throws PaymentManagementException;

  public List<CustPmtMap> getPaymentMap(User user) throws PaymentManagementException;

  public CustPmtMap getPaymentMap(User user, int paymentId) throws PaymentManagementException;

  public List<CustPmtMap> updatePaymentMap(CustPmtMap custPmtMap) throws PaymentManagementException;
}