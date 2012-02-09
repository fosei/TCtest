package com.trc.exception.service;

import com.trc.exception.ServiceException;

public class DeviceServiceException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public DeviceServiceException() {
    super();
  }

  public DeviceServiceException(String message) {
    super(message);
  }

  public DeviceServiceException(Throwable cause) {
    super(cause);
  }

  public DeviceServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
