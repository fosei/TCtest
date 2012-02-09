package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class AddressManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public AddressManagementException() {
    super();
  }

  public AddressManagementException(String message) {
    super(message);
  }

  public AddressManagementException(Throwable cause) {
    super(cause);
  }

  public AddressManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
