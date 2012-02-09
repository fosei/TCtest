package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class AddressServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public AddressServiceException() {
    super();
  }

  public AddressServiceException(String message) {
    super(message);
  }

  public AddressServiceException(Throwable cause) {
    super(cause);
  }

  public AddressServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
