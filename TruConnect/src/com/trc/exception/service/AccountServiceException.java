package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class AccountServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public AccountServiceException() {
    super();
  }

  public AccountServiceException(String message) {
    super(message);
  }

  public AccountServiceException(Throwable cause) {
    super(cause);
  }

  public AccountServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
