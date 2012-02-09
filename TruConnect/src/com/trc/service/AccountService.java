package com.trc.service;

import java.util.List;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.service.AccountServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.util.Formatter;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.Customer;
import com.tscp.mvne.PaymentRecord;
import com.tscp.mvne.TruConnect;
import com.tscp.mvne.UsageDetail;

@Service
public class AccountService implements AccountServiceModel {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  private Account makeAccount(User user) {
    Account account = new Account();
    account.setFirstname(user.getContactInfo().getFirstName());
    account.setLastname(user.getContactInfo().getLastName());
    account.setContactAddress1(user.getContactInfo().getAddress().getAddress1());
    account.setContactAddress2(user.getContactInfo().getAddress().getAddress2());
    account.setContactCity(user.getContactInfo().getAddress().getCity());
    account.setContactState(user.getContactInfo().getAddress().getState());
    account.setContactZip(user.getContactInfo().getAddress().getZip());
    account.setContactNumber(user.getContactInfo().getPhoneNumber());
    account.setContactEmail(user.getEmail());
    return account;
  }

  @Override
  public CustInfo getCustInfo(User user) throws AccountServiceException {
    try {
      return truConnect.getCustInfo(TruConnectUtil.toCustomer(user));
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public Account createShellAccount(User user) throws AccountServiceException {
    Account account = makeAccount(user);
    try {
      account = truConnect.createBillingAccount(TruConnectUtil.toCustomer(user), account);
      setTopUp(user, 10.00, account);
      return account;
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<CustAcctMapDAO> getAccountMap(User user) throws AccountServiceException {
    Customer customer = TruConnectUtil.toCustomer(user);
    try {
      return truConnect.getCustomerAccounts(customer.getId());
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<UsageDetail> getChargeHistory(User user, int accountNumber) throws AccountServiceException {
    try {
      return truConnect.getCustomerChargeHistory(TruConnectUtil.toCustomer(user), accountNumber, null);
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<PaymentRecord> getPaymentRecords(User user) throws AccountServiceException {
    try {
      return truConnect.getPaymentHistory(TruConnectUtil.toCustomer(user));
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void updateEmail(Account account) throws AccountServiceException {
    try {
      truConnect.updateAccountEmailAddress(account);
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public Account getAccount(int accountNumber) throws AccountServiceException {
    try {
      return truConnect.getAccountInfo(accountNumber);
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public CustTopUp getTopUp(User user, Account account) throws AccountServiceException {
    try {
      return truConnect.getCustTopUpAmount(TruConnectUtil.toCustomer(user), account);
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public CustTopUp setTopUp(User user, double amount, Account account) throws AccountServiceException {
    try {
      String stringAmount = Formatter.formatDollarAmount(amount);
      return truConnect.setCustTopUpAmount(TruConnectUtil.toCustomer(user), stringAmount, account);
    } catch (WebServiceException e) {
      throw new AccountServiceException(e.getMessage(), e.getCause());
    }
  }

}
