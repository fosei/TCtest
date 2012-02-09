package com.trc.manager;

import java.util.List;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.PaymentRecord;
import com.tscp.mvne.UsageDetail;

public interface AccountManagerModel {

  public Account createShellAccount(User user) throws AccountManagementException, AddressManagementException;

  public CustInfo getCustInfo(User user) throws AccountManagementException;

  public List<CustAcctMapDAO> getAccountMap(User user) throws AccountManagementException;

  public List<UsageDetail> getChargeHistory(User user, int accountNumber) throws AccountManagementException;

  public List<PaymentRecord> getPaymentRecords(User user) throws AccountManagementException;

  public void updateEmail(Account account) throws AccountManagementException;

  public Account getAccount(int accountNumber) throws AccountManagementException;

  public CustTopUp getTopUp(User user, Account account) throws AccountManagementException;

  public CustTopUp setTopUp(User user, double amount, Account account) throws AccountManagementException;
}
