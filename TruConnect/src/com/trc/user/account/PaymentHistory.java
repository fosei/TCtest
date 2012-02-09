package com.trc.user.account;

import java.util.List;

import com.trc.exception.management.AccountManagementException;
import com.trc.manager.AccountManager;
import com.trc.user.User;
import com.trc.util.Paginator;
import com.tscp.mvne.PaymentRecord;

/**
 * PaymentHistory is a paginated list of the user's paymentRecords.
 * 
 * @author Tachikoma
 * 
 */
public class PaymentHistory extends Paginator {

  public PaymentHistory(List<PaymentRecord> paymentRecords, User user) {
    super.setRecords(paymentRecords);
    super.setSummarySize(3);
  }

  @Deprecated
  public PaymentHistory(AccountManager accountManager, User user) throws AccountManagementException {
    List<PaymentRecord> paymentRecords;
    try {
      paymentRecords = accountManager.getPaymentRecords(user);
      super.setRecords(paymentRecords);
      super.setSummarySize(3);
    } catch (AccountManagementException e) {
      throw e;
    }
  }
}