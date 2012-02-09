package com.trc.util.logger;

import java.text.MessageFormat;

import org.apache.log4j.Level;

// @Component
// Using Slf4jLogger for logback
@Deprecated
public class Log4jLogger implements Logger {

  @Override
  public boolean isLogLevel(LogLevel logLevel, Class<?> clazz) {
    boolean result = false;
    switch (logLevel) {
    case DEBUG:
      result = getLogger(clazz).isDebugEnabled();
    case ERROR:
      result = getLogger(clazz).isEnabledFor(Level.ERROR);
    case FATAL:
      result = getLogger(clazz).isEnabledFor(Level.FATAL);
    case INFO:
      result = getLogger(clazz).isInfoEnabled();
    case TRACE:
      result = getLogger(clazz).isTraceEnabled();
    case WARN:
      result = getLogger(clazz).isEnabledFor(Level.WARN);
    default:
      result = false;
    }
    return result;
  }

  @Override
  public void log(LogLevel logLevel, Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    switch (logLevel) {
    case DEBUG:
      debug(clazz, throwable, pattern, arguments);
      break;
    case ERROR:
      error(clazz, throwable, pattern, arguments);
      break;
    case FATAL:
      fatal(clazz, throwable, pattern, arguments);
      break;
    case INFO:
      info(clazz, throwable, pattern, arguments);
      break;
    case TRACE:
      trace(clazz, throwable, pattern, arguments);
      break;
    case WARN:
      warn(clazz, throwable, pattern, arguments);
      break;
    }
  }

  private void debug(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).debug(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).debug(format(pattern, arguments));
    }
  }

  private void error(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).error(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).error(format(pattern, arguments));
    }
  }

  private void fatal(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).fatal(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).fatal(format(pattern, arguments));
    }
  }

  private void info(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).info(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).info(format(pattern, arguments));
    }
  }

  private void trace(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).trace(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).trace(format(pattern, arguments));
    }
  }

  private void warn(Class<?> clazz, Throwable throwable, String pattern, Object... arguments) {
    if (throwable != null) {
      getLogger(clazz).warn(format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).warn(format(pattern, arguments));
    }
  }

  private String format(String pattern, Object... arguments) {
    return MessageFormat.format(pattern, arguments);
  }

  private org.apache.log4j.Logger getLogger(Class<?> clazz) {
    return org.apache.log4j.Logger.getLogger(clazz);
  }
}