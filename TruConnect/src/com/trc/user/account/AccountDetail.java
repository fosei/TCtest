package com.trc.user.account;

import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;

/**
 * AccountDetail contains all information for a given account.
 * 
 * @author Tachikoma
 * 
 */
public class AccountDetail {
  private Account account;
  private UsageHistory usageHistory;
  private DeviceInfo deviceInfo;
  private String topUp;
  private String encodedDeviceId;
  private String encodedAccountNum;

  public String getEncodedAccountNum() {
    return encodedAccountNum;
  }

  public void setEncodedAccountNum(String encodedAccountNum) {
    this.encodedAccountNum = encodedAccountNum;
  }

  public String getEncodedDeviceId() {
    return encodedDeviceId;
  }

  public void setEncodedDeviceId(String encodedDeviceId) {
    this.encodedDeviceId = encodedDeviceId;
  }

  public String getTopUp() {
    return topUp;
  }

  public void setTopUp(String topUp) {
    this.topUp = topUp;
  }

  public UsageHistory getUsageHistory() {
    return usageHistory;
  }

  public void setUsageHistory(UsageHistory usageHistory) {
    this.usageHistory = usageHistory;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public DeviceInfo getDeviceInfo() {
    return deviceInfo;
  }

  public void setDeviceInfo(DeviceInfo deviceInfo) {
    this.deviceInfo = deviceInfo;
  }
}
