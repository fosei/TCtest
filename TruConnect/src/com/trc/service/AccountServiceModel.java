package com.trc.service;

import java.util.List;

import com.trc.exception.service.AccountServiceException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.PaymentRecord;
import com.tscp.mvne.UsageDetail;

public interface AccountServiceModel {

  public Account createShellAccount(User user) throws AccountServiceException;

  public CustInfo getCustInfo(User user) throws AccountServiceException;

  public List<CustAcctMapDAO> getAccountMap(User user) throws AccountServiceException;

  public List<UsageDetail> getChargeHistory(User user, int accountNumber) throws AccountServiceException;

  public List<PaymentRecord> getPaymentRecords(User user) throws AccountServiceException;

  public void updateEmail(Account account) throws AccountServiceException;

  public Account getAccount(int accountNumber) throws AccountServiceException;

  public CustTopUp getTopUp(User user, Account account) throws AccountServiceException;

  public CustTopUp setTopUp(User user, double amount, Account account) throws AccountServiceException;

}
