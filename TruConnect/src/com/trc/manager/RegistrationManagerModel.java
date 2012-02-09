package com.trc.manager;

import com.trc.exception.EmailException;
import com.trc.exception.management.UserManagementException;
import com.trc.user.User;
import com.trc.user.activation.Registration;
import com.tscp.mvne.Account;

public interface RegistrationManagerModel {

  public abstract void reserveUserId(Registration registration) throws UserManagementException;

  public abstract void sendActivationEmail(User user, Account account);

  public abstract void sendActivationEmail(User user) throws EmailException;

  public abstract void cancelRegistration(Registration registration) throws UserManagementException;

}