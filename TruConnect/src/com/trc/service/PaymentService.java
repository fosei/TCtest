package com.trc.service;

import java.util.List;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.service.PaymentServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.web.session.SessionManager;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.CustPmtMap;
import com.tscp.mvne.PaymentUnitResponse;
import com.tscp.mvne.TruConnect;

@Service
public class PaymentService implements PaymentServiceModel {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  @Override
  public CreditCard getCreditCard(int paymentId) throws PaymentServiceException {
    try {
      CreditCard creditCard = truConnect.getCreditCardDetail(paymentId);
      if (creditCard.getAddress2() != null && creditCard.getAddress2().equals("{}")) {
        creditCard.setAddress2(null);
      }
      return creditCard;
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public CreditCard addCreditCard(User user, CreditCard creditCard) throws PaymentServiceException {
    try {
      if (user == null || creditCard.getIsDefault() == null) {
        creditCard.setIsDefault("N");
      }
      return truConnect.addCreditCard(TruConnectUtil.toCustomer(user), creditCard);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<CustPmtMap> removeCreditCard(User user, int paymentId) throws PaymentServiceException {
    try {
      List<CustPmtMap> paymentMapList = truConnect.deleteCreditCardPaymentMethod(TruConnectUtil.toCustomer(user),
          paymentId);
      if (!paymentMapList.isEmpty()) {
        boolean updateDefault = true;
        CustPmtMap newDefault = paymentMapList.get(0);
        for (CustPmtMap paymentMap : paymentMapList) {
          if (paymentMap.getIsDefault().equals("Y")) {
            updateDefault = false;
          }
        }
        if (updateDefault) {
          newDefault.setIsDefault("Y");
          return updatePaymentMap(newDefault);
        }
      }
      return paymentMapList;
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<CustPmtMap> updateCreditCard(User user, CreditCard creditCard) throws PaymentServiceException {
    try {
      if (creditCard.getAddress2() == null || creditCard.getAddress2().isEmpty()) {
        creditCard.setAddress2("{}");
      }
      if (creditCard.getCreditCardNumber().toLowerCase().contains("x")) {
        creditCard.setCreditCardNumber(null);
      }
      List<CustPmtMap> paymentMapList = truConnect.updateCreditCardPaymentMethod(TruConnectUtil.toCustomer(user),
          creditCard);
      CustPmtMap paymentMap = getPaymentMap(paymentMapList, creditCard.getPaymentid());
      paymentMap.setPaymentalias(creditCard.getAlias());
      if (creditCard.getIsDefault() == null) {
        paymentMap.setIsDefault("N");
      } else {
        paymentMap.setIsDefault("Y");
      }
      return updatePaymentMap(paymentMap);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  private CustPmtMap getPaymentMap(List<CustPmtMap> paymentMapList, int paymentId) {
    for (CustPmtMap custPmtMap : paymentMapList) {
      if (custPmtMap.getPaymentid() == paymentId)
        return custPmtMap;
    }
    return null;
  }

  @Override
  public List<CustPmtMap> getPaymentMap(User user) throws PaymentServiceException {
    try {
      return truConnect.getCustPaymentList(user.getUserId(), 0);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public CustPmtMap getPaymentMap(User user, int paymentId) throws PaymentServiceException {
    try {
      List<CustPmtMap> result = truConnect.getCustPaymentList(user.getUserId(), paymentId);
      return result.size() == 1 ? result.get(0) : null;
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<CustPmtMap> updatePaymentMap(CustPmtMap custPmtMap) throws PaymentServiceException {
    try {
      return truConnect.updateCustPaymentMap(custPmtMap);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public PaymentUnitResponse makePayment(User user, Account account, CreditCard creditCard, String amount)
      throws PaymentServiceException {
    try {
      return truConnect.makeCreditCardPayment(SessionManager.getCurrentSessionId(), account, creditCard, amount);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public PaymentUnitResponse makePayment(User user, Account account, int paymentId, String amount)
      throws PaymentServiceException {
    try {
      return truConnect.submitPaymentByPaymentId(SessionManager.getCurrentSessionId(), TruConnectUtil.toCustomer(user),
          paymentId, account, amount);
    } catch (WebServiceException e) {
      throw new PaymentServiceException(e.getMessage(), e.getCause());
    }
  }

}
