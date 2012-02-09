package com.trc.exception;

public class GatewayException extends Exception {
  private static final long serialVersionUID = 1L;

  public GatewayException() {
    super();
  }

  public GatewayException(String message) {
    super(message);
  }

  public GatewayException(Throwable cause) {
    super(cause);
  }

  public GatewayException(String message, Throwable cause) {
    super(message, cause);
  }

}
