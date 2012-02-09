package com.trc.web.validation;

import java.util.Collection;

import org.springframework.validation.ValidationUtils;

import com.trc.security.encryption.Md5Encoder;

public class ValidationUtil extends ValidationUtils {

  public static boolean isCorrectPassword(String enteredPassword, String storedPassword) {
    return Md5Encoder.encode(enteredPassword).equals(storedPassword);
  }

  public static boolean matches(String value1, String value2) {
    return value1.equals(value2);
  }

  public static boolean isEmpty(@SuppressWarnings("rawtypes") Collection values) {
    return values == null || values.isEmpty();
  }

  public static boolean isEmpty(String value) {
    return value == null || value.trim().isEmpty();
  }

  public static boolean isBetween(String value, int min, int max) {
    if (value == null) {
      return false;
    } else {
      return value.length() >= min && value.length() <= max;
    }
  }

  public static boolean isAlphaNumeric(String value) {
    return !isNumeric(value) && !isAlpha(value);
  }

  public static boolean isNumeric(String value) {
    return value.matches("\\d+");
  }

  public static boolean isAlpha(String value) {
    return value.matches("\\D+");
  }
}
