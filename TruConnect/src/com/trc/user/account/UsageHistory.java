package com.trc.user.account;

import java.util.List;

import com.trc.exception.management.AccountManagementException;
import com.trc.manager.AccountManager;
import com.trc.user.User;
import com.trc.util.Paginator;
import com.tscp.mvne.UsageDetail;

/**
 * UsageHistory is a paginated list of the user's usageDetails.
 * 
 * @author Tachikoma
 * 
 */
public class UsageHistory extends Paginator {

  public UsageHistory(List<UsageDetail> usageDetails, User user, int accountNumber) {
    super.setRecords(usageDetails);
    super.setSummarySize(3);
  }

  @Deprecated
  public UsageHistory(AccountManager accountManager, User user, int accountNumber) throws AccountManagementException {
    List<UsageDetail> usageDetails;
    try {
      usageDetails = accountManager.getChargeHistory(user, accountNumber);
      super.setRecords(usageDetails);
      super.setSummarySize(3);
    } catch (AccountManagementException e) {
      throw e;
    }
  }

}
