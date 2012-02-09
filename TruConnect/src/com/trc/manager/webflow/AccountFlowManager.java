package com.trc.manager.webflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.WebFlowException;
import com.trc.exception.management.AccountManagementException;
import com.trc.exception.management.AddressManagementException;
import com.trc.manager.AccountManager;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.user.contact.ContactInfo;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;

@Component
public class AccountFlowManager {
  @Autowired
  private AccountManager accountManager;

  private static final String ERROR_CONTACT_INFO = "An error occurred while retrieving your contact information";
  private static final String ERROR_CREATE_SHELL = "An error occurred while creating your account. Please re-enter the below information and try again.";

  public ContactInfo getDefaultContactInfo(User user) throws WebFlowException {
    try {
      return accountManager.getDefaultContactInfo(user);
    } catch (AccountManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_CONTACT_INFO);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (AddressManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_CONTACT_INFO);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void createShellAccount(User user, Account account) throws WebFlowException {
    try {
      Account createdAccount = accountManager.createShellAccount(user);
      TruConnectUtil.copyAccount(account, createdAccount);
    } catch (AccountManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_CREATE_SHELL);
      throw new WebFlowException(e.getMessage(), e.getCause());
    } catch (AddressManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_CREATE_SHELL);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }
}