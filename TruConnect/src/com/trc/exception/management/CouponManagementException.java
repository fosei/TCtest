package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class CouponManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public CouponManagementException() {
    super();
  }

  public CouponManagementException(String message) {
    super(message);
  }

  public CouponManagementException(Throwable cause) {
    super(cause);
  }

  public CouponManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
