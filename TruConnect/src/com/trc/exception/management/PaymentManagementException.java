package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class PaymentManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public PaymentManagementException() {
    super();
  }

  public PaymentManagementException(String message) {
    super(message);
  }

  public PaymentManagementException(Throwable cause) {
    super(cause);
  }

  public PaymentManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
