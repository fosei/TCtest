package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class AccountManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public AccountManagementException() {
    super();
  }

  public AccountManagementException(String message) {
    super(message);
  }

  public AccountManagementException(Throwable cause) {
    super(cause);
  }

  public AccountManagementException(String message, Throwable cause) {
    super(message, cause);
  }

}
