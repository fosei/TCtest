package com.trc.service.jms.message;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import com.tscp.mvne.Account;
import com.tscp.mvne.NetworkInfo;

public class KenanServiceInstance implements Serializable {
  private static final long serialVersionUID = -4483145089301503538L;
  private NetworkInfo networkInfo;
  private Account account;
  private AtomicInteger attemptNumber = new AtomicInteger(1);

  public NetworkInfo getNetworkInfo() {
    return networkInfo;
  }

  public void setNetworkInfo(NetworkInfo networkInfo) {
    this.networkInfo = networkInfo;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public int getAttemptNumber() {
    return attemptNumber.get();
  }

  public void setAttemptNumber(int attemptNumber) {
    this.attemptNumber.set(attemptNumber);
  }

  public void incrementAttempt() {
    this.attemptNumber.incrementAndGet();
  }

  public void decrementAttempt() {
    this.attemptNumber.decrementAndGet();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((account == null) ? 0 : account.hashCode());
    result = prime * result + ((attemptNumber == null) ? 0 : attemptNumber.hashCode());
    result = prime * result + ((networkInfo == null) ? 0 : networkInfo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    KenanServiceInstance other = (KenanServiceInstance) obj;
    if (account == null) {
      if (other.account != null)
        return false;
    } else if (!account.equals(other.account))
      return false;
    if (attemptNumber == null) {
      if (other.attemptNumber != null)
        return false;
    } else if (!attemptNumber.equals(other.attemptNumber))
      return false;
    if (networkInfo == null) {
      if (other.networkInfo != null)
        return false;
    } else if (!networkInfo.equals(other.networkInfo))
      return false;
    return true;
  }

}
