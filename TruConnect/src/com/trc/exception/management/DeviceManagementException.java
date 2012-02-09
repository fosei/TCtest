package com.trc.exception.management;

import com.trc.exception.ManagementException;

public class DeviceManagementException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public DeviceManagementException() {
    super();
  }

  public DeviceManagementException(String message) {
    super(message);
  }

  public DeviceManagementException(Throwable cause) {
    super(cause);
  }

  public DeviceManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
