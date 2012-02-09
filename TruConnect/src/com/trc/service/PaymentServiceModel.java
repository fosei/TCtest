package com.trc.service;

import java.util.List;

import com.trc.exception.service.PaymentServiceException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.CustPmtMap;
import com.tscp.mvne.PaymentInformation;
import com.tscp.mvne.PaymentUnitResponse;

public interface PaymentServiceModel {

  public PaymentUnitResponse makePayment(User user, Account account, CreditCard creditCard, String amount)
      throws PaymentServiceException;

  public PaymentUnitResponse makePayment(User user, Account account, int paymentId, String amount)
      throws PaymentServiceException;

  public PaymentInformation getCreditCard(int paymentId) throws PaymentServiceException;

  public CreditCard addCreditCard(User user, CreditCard creditCard) throws PaymentServiceException;

  public List<CustPmtMap> removeCreditCard(User user, int paymentId) throws PaymentServiceException;

  public List<CustPmtMap> updateCreditCard(User user, CreditCard creditCard) throws PaymentServiceException;

  public List<CustPmtMap> getPaymentMap(User user) throws PaymentServiceException;

  public CustPmtMap getPaymentMap(User user, int paymentId) throws PaymentServiceException;

  public List<CustPmtMap> updatePaymentMap(CustPmtMap custPmtMap) throws PaymentServiceException;

}
