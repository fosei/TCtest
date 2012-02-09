package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class PaymentServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public PaymentServiceException() {
    super();
  }

  public PaymentServiceException(String message) {
    super(message);
  }

  public PaymentServiceException(Throwable cause) {
    super(cause);
  }

  public PaymentServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
