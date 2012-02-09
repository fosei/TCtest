package com.trc.util.logger;

import java.text.MessageFormat;

import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Component
public class Slf4jLogger implements Logger {
  private static final Marker fatal = MarkerFactory.getMarker("FATAL");

  @Override
  public boolean isLogLevel(LogLevel logLevel, Class<?> clazz) {
    boolean result = false;
    switch (logLevel) {
    case DEBUG:
      result = getLogger(clazz).isDebugEnabled();
    case ERROR:
      result = getLogger(clazz).isErrorEnabled();
    case FATAL:
      result = true;
    case INFO:
      result = getLogger(clazz).isInfoEnabled();
    case TRACE:
      result = getLogger(clazz).isTraceEnabled();
    case WARN:
      result = getLogger(clazz).isWarnEnabled();
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
      getLogger(clazz).error(fatal, format(pattern, arguments), throwable);
    } else {
      getLogger(clazz).error(fatal, format(pattern, arguments));
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

  public org.slf4j.Logger getLogger(Class<?> clazz) {
    return LoggerFactory.getLogger("truconnect");
  }

}