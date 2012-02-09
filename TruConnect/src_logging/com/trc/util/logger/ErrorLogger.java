package com.trc.util.logger;

import org.slf4j.LoggerFactory;

public class ErrorLogger {
  private static org.slf4j.Logger logger = LoggerFactory.getLogger("errorLogger");

  public static org.slf4j.Logger getInstance() {
    return logger;
  }

}