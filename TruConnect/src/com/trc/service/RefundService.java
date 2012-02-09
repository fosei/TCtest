package com.trc.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.service.RefundServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.util.Formatter;
import com.trc.util.logger.DevLogger;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.KenanPayment;
import com.tscp.mvne.TruConnect;

@Service
public class RefundService {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  public List<KenanPayment> getPayments(Account account) throws RefundServiceException {
    try {
      return truConnect.getKenanPayments(account);
    } catch (WebServiceException e) {
      throw new RefundServiceException(e.getMessage(), e.getCause());
    }
  }

  public void applyCredit(CreditCard creditCard, Double amount) throws RefundServiceException {
    try {
      String stringAmount = Formatter.formatDollarAmount(amount);
      truConnect.applyChargeCredit(creditCard, stringAmount);
    } catch (WebServiceException e) {
      throw new RefundServiceException(e.getMessage(), e.getCause());
    }
  }

  public void reversePayment(Account account, Double amount, Date date, String trackingId)
      throws RefundServiceException {
    try {
      GregorianCalendar calendar = new GregorianCalendar();
      calendar.setTime(date);
      XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
      truConnect.reverseKenanPayment(account, Double.toString(amount), xmlCal, trackingId);
    } catch (DatatypeConfigurationException dce) {
      throw new RefundServiceException("Error reversing payment: could not create XMLGregorianCalendar");
    } catch (WebServiceException e) {
      throw new RefundServiceException(e.getMessage(), e.getCause());
    }
  }
}
