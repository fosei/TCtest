package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class CouponServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public CouponServiceException() {
    super();
  }

  public CouponServiceException(String message) {
    super(message);
  }

  public CouponServiceException(Throwable cause) {
    super(cause);
  }

  public CouponServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
