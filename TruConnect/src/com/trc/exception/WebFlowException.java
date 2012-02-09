package com.trc.exception;

public class WebFlowException extends ManagementException {
  private static final long serialVersionUID = 1L;

  public WebFlowException() {
    super();
  }

  public WebFlowException(String message, Throwable cause) {
    super(message, cause);
  }

  public WebFlowException(String message) {
    super(message);
  }

  public WebFlowException(Throwable cause) {
    super(cause);
  }

}