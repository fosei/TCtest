package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class UserManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public UserManagementException() {
    super();
  }

  public UserManagementException(String message) {
    super(message);
  }

  public UserManagementException(Throwable cause) {
    super(cause);
  }

  public UserManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
