package com.trc.exception;

public class EmailException extends Exception {
  private static final long serialVersionUID = 1L;

  public EmailException() {
    super();
  }

  public EmailException(String message) {
    super(message);
  }

  public EmailException(Throwable cause) {
    super(cause);
  }

  public EmailException(String message, Throwable cause) {
    super(message, cause);
  }
}
