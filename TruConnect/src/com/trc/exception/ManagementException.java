package com.trc.exception;

public class ManagementException extends ServiceException {
  private static final long serialVersionUID = 1L;

  public ManagementException() {
    super();
  }

  public ManagementException(String message) {
    super(message);
  }

  public ManagementException(Throwable cause) {
    super(cause);
  }

  public ManagementException(String message, Throwable cause) {
    super(message, cause);
  }
}
