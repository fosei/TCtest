package com.trc.util.logger;

import org.slf4j.LoggerFactory;

public class DevLogger {
  private static org.slf4j.Logger logger = LoggerFactory.getLogger("devLogger");

  public static void log(String message) {
    logger.debug(message);
  }

  public static void log(String message, Throwable throwable) {
    logger.debug(message, throwable);
  }

  public static void debug(String message) {
    logger.debug(message);
  }

  public static void debug(String message, Throwable throwable) {
    logger.debug(message, throwable);
  }

  public static void error(String message) {
    logger.error(message);
  }

  public static void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }
}