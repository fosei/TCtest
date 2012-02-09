package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class RefundServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public RefundServiceException() {
    super();
  }

  public RefundServiceException(String message) {
    super(message);
  }

  public RefundServiceException(Throwable cause) {
    super(cause);
  }

  public RefundServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
