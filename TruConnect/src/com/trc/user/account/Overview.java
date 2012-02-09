package com.trc.user.account;

import java.util.ArrayList;
import java.util.List;

import com.trc.exception.management.AccountManagementException;
import com.trc.manager.AccountManager;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;

/**
 * Overview contains all accountDetails and the paymentHistory for the given
 * user.
 * 
 * @author Tachikoma
 * 
 */
public class Overview {
  private List<AccountDetail> accountDetails;
  private PaymentHistory paymentHistory;

  public Overview(AccountManager accountManager, List<DeviceInfo> devices, User user) {
    this.accountDetails = new ArrayList<AccountDetail>();

    AccountDetail accountDetail;
    Account account;

    try {
      // devices = accountManager.getDeviceList(user);
      this.paymentHistory = new PaymentHistory(accountManager.getPaymentRecords(user), user);
      for (DeviceInfo deviceInfo : devices) {
        account = accountManager.getAccount(deviceInfo.getAccountNo());
        accountDetail = new AccountDetail();
        accountDetail.setAccount(account);
        accountDetail.setDeviceInfo(deviceInfo);
        accountDetail.setTopUp(accountManager.getTopUp(user, account).getTopupAmount());
        accountDetail.setUsageHistory(new UsageHistory(accountManager.getChargeHistory(user, account.getAccountno()),
            user, account.getAccountno()));
        this.accountDetails.add(accountDetail);
      }
    } catch (AccountManagementException e) {
      e.printStackTrace();
    }
  }

  public PaymentHistory getPaymentDetails() {
    return paymentHistory;
  }

  public void setPaymentDetails(PaymentHistory paymentDetails) {
    this.paymentHistory = paymentDetails;
  }

  public AccountDetail getAccountDetail(int accountNum) {
    for (AccountDetail accountDetail : accountDetails) {
      if (accountDetail.getAccount().getAccountno() == accountNum) {
        return accountDetail;
      }
    }
    return null;
  }

  public List<AccountDetail> getAccountDetails() {
    return accountDetails;
  }

  public void setAccountDetails(List<AccountDetail> accountDetails) {
    this.accountDetails = accountDetails;
  }

}