package com.trc.manager;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.service.AccountServiceException;
import com.trc.service.AccountService;
import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.account.Overview;
import com.trc.user.account.PaymentHistory;
import com.trc.user.account.UsageHistory;
import com.trc.user.contact.Address;
import com.trc.user.contact.ContactInfo;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.session.cache.CacheKey;
import com.trc.web.session.cache.CacheManager;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustInfo;
import com.tscp.mvne.CustTopUp;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.PaymentRecord;
import com.tscp.mvne.UsageDetail;

@Component
@SuppressWarnings("unchecked")
public class AccountManager implements AccountManagerModel {
  @Autowired
  private AccountService accountService;
  @Autowired
  private AddressManager addressManager;
  @Autowired
  private DeviceManager deviceManager;

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account createShellAccount(User user) throws AccountManagementException, AddressManagementException {
    try {
      Account account = accountService.createShellAccount(user);
      return account;
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustInfo getCustInfo(User user) throws AccountManagementException {
    try {
      return accountService.getCustInfo(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public AccountDetail getAccountDetail(User user, DeviceInfo deviceInfo) throws AccountManagementException {
    int accountNumber = deviceInfo.getAccountNo();
    try {
      Account account = getAccount(accountNumber);
      AccountDetail accountDetail = new AccountDetail();
      accountDetail.setDeviceInfo(deviceInfo);
      accountDetail.setAccount(account);
      accountDetail.setTopUp(getTopUp(user, account).getTopupAmount());
      accountDetail.setUsageHistory(new UsageHistory(getChargeHistory(user, account.getAccountno()), user, account
          .getAccountno()));
      return accountDetail;
    } catch (AccountManagementException e) {
      throw e;
    }
  }

  public AccountDetail getAccountDetail(User user, int deviceId) throws AccountManagementException {
    try {
      return getAccountDetail(user, deviceManager.getDeviceInfo(user, deviceId));
    } catch (DeviceManagementException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<AccountDetail> getAccountDetailList(User user) throws AccountManagementException {
    List<DeviceInfo> deviceList;

    try {
      deviceList = deviceManager.getDeviceInfoList(user);
    } catch (DeviceManagementException e) {
      deviceList = new ArrayList<DeviceInfo>();
    }

    List<AccountDetail> accountDetailList = new ArrayList<AccountDetail>();
    AccountDetail accountDetail;
    try {
      for (DeviceInfo deviceInfo : deviceList) {
        accountDetail = getAccountDetail(user, deviceInfo);
        accountDetailList.add(accountDetail);
      }
    } catch (AccountManagementException e) {
      throw e;
    }
    return accountDetailList;
  }

  private List<Account> getAccountListFromCache() {
    return (List<Account>) CacheManager.get(CacheKey.ACCOUNTS);
  }

  private Account getAccountFromCache(int accountNumber) {
    List<Account> accountList = getAccountListFromCache();
    if (accountList != null) {
      for (Account account : accountList) {
        if (account.getAccountno() == accountNumber) {
          return account;
        }
      }
    }
    return null;
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account getAccount(int accountNumber) throws AccountManagementException {
    Account account = getAccountFromCache(accountNumber);
    if (account != null) {
      return account;
    } else {
      try {
        return accountService.getAccount(accountNumber);
      } catch (AccountServiceException e) {
        throw new AccountManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<CustAcctMapDAO> getAccountMap(User user) throws AccountManagementException {
    try {
      return accountService.getAccountMap(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public List<Account> getAccounts(User user) throws AccountManagementException {
    List<Account> accountList = getAccountListFromCache();
    if (accountList != null) {
      return accountList;
    } else {
      try {
        accountList = new ArrayList<Account>();
        for (CustAcctMapDAO accountMap : getAccountMap(user)) {
          accountList.add(getAccount(accountMap.getAccountNo()));
        }
        CacheManager.set(CacheKey.ACCOUNTS, accountList);
        return accountList;
      } catch (AccountManagementException e) {
        throw e;
      }
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<UsageDetail> getChargeHistory(User user, int accountNumber) throws AccountManagementException {
    try {
      return accountService.getChargeHistory(user, accountNumber);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public ContactInfo getDefaultContactInfo(User user) throws AccountManagementException, AddressManagementException {
    List<Account> accountList;
    try {
      accountList = getAccounts(user);
      Account account = accountList.get(0);
      Address address = addressManager.getDefaultAddress(user);
      ContactInfo contactInfo = new ContactInfo();
      contactInfo.setAddress(address);
      contactInfo.setFirstName(account.getFirstname());
      contactInfo.setLastName(account.getLastname());
      contactInfo.setPhoneNumber(account.getContactNumber());
      return contactInfo;
    } catch (AccountManagementException e) {
      throw e;
    } catch (AddressManagementException e) {
      throw e;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public XMLGregorianCalendar getLastAccessFeeDate(User user, Account account) {
    XMLGregorianCalendar accessFeeDate = null;
    List<UsageDetail> usageDetails;
    try {
      usageDetails = getChargeHistory(user, account.getAccountno());
    } catch (AccountManagementException e) {
      usageDetails = new ArrayList<UsageDetail>();
    }
    for (UsageDetail usageDetail : usageDetails) {
      if (accessFeeDate == null && usageDetail.getUsageType().equals("Access Fee")) {
        accessFeeDate = usageDetail.getEndTime();
        break;
      }
    }
    return accessFeeDate;
  }

  @Loggable(value = LogLevel.TRACE)
  public int getNumAccounts(User user) {
    try {
      List<CustAcctMapDAO> accounts = getAccountMap(user);
      return accounts != null ? accounts.size() : 0;
    } catch (AccountManagementException e) {
      return 0;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public Overview getOverview(User user) {
    List<DeviceInfo> deviceInfoList;
    try {
      deviceInfoList = deviceManager.getDeviceInfoList(user);
    } catch (DeviceManagementException e) {
      deviceInfoList = new ArrayList<DeviceInfo>();
    }
    return new Overview(this, deviceInfoList, user);
  }

  @Loggable(value = LogLevel.TRACE)
  public PaymentHistory getPaymentHistory(User user) throws AccountManagementException {
    try {
      return new PaymentHistory(getPaymentRecords(user), user);
    } catch (AccountManagementException e) {
      throw e;
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<PaymentRecord> getPaymentRecords(User user) throws AccountManagementException {
    try {
      return accountService.getPaymentRecords(user);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustTopUp getTopUp(User user, Account account) throws AccountManagementException {
    try {
      return accountService.getTopUp(user, account);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public CustTopUp setTopUp(User user, double amount, Account account) throws AccountManagementException {
    try {
      return accountService.setTopUp(user, amount, account);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void updateEmail(Account account) throws AccountManagementException {
    try {
      accountService.updateEmail(account);
      CacheManager.clear(CacheKey.ACCOUNTS);
    } catch (AccountServiceException e) {
      throw new AccountManagementException(e.getMessage(), e.getCause());
    }
  }

}
