package com.trc.web.validation;

import java.text.ParseException;
import java.util.Date;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.contact.Address;
import com.trc.util.SimpleDate;
import com.tscp.mvne.CreditCard;

@Component
public class CreditCardValidator extends AddressValidator {
  public static final int americanExpress = 3;
  public static final int visa = 4;
  public static final int masterCard = 5;
  public static final int discover = 6;

  @Override
  public boolean supports(Class<?> myClass) {
    return CreditCard.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    CreditCard creditCard = (CreditCard) target;
    checkName(creditCard.getNameOnCreditCard(), errors);
    checkCreditCardNumber(creditCard.getCreditCardNumber(), errors);
    checkCvv(creditCard.getVerificationcode(), errors);
    checkExpirationDate(creditCard.getExpirationDate(), errors);
    checkAddress(creditCard, errors);
  }

  // public void choosePaymentMethod(CreditCard creditCard, ValidationContext
  // context) {
  // MessageContext messages = context.getMessageContext();
  // if (creditCard.getPaymentid() == 0) {
  // WebFlowUtil.addError(messages, "paymentid", "creditCard.paymentId",
  // "You must choose a payment option");
  // }
  // }

  public static void checkName(String name, Errors errors) {
    if (ValidationUtil.isEmpty(name)) {
      errors.rejectValue("nameOnCreditCard", "creditCard.name.required", "You must enter the name on the card");
    } else if (!ValidationUtil.isBetween(name, 3, 100)) {
      errors.rejectValue("nameOnCreditCard", "creditCard.name.size", "Name must be at least 3 characters");
    }
  }

  public void checkCreditCardNumber(String cardNumber, Errors errors) {
    if (cardNumber.toLowerCase().contains("x")) {
      return;
    } else if (ValidationUtil.isEmpty(cardNumber)) {
      errors.rejectValue("creditCardNumber", "creditCard.number.required", "You must enter a credit card number");
    } else if (!ValidationUtil.isBetween(cardNumber, 15, 16)) {
      errors
          .rejectValue("creditCardNumber", "creditCard.number.size", "You have entered an invalid credit card number");
    } else if (!mod10(cardNumber)) {
      errors.rejectValue("creditCardNumber", "creditCard.number.invalid",
          "You have entered an invalid credit card number");
    }
  }

  public void checkCvv(String cvv, Errors errors) {
    if (ValidationUtil.isEmpty(cvv)) {
      errors.rejectValue("verificationcode", "creditCard.verificationCode.required", "You must enter a CVV number");
    }
  }

  private void checkExpirationDate(String date, Errors errors) {
    if (!date.matches("\\d{4}")) {
      errors.rejectValue("expirationDate", "creditCard.expiration.invalid", "Invalid date");
    } else {
      int month = Integer.parseInt(date.substring(0, 2));
      if (month < 1 || month > 12) {
        errors.rejectValue("expirationDate", "creditCard.expiration.month.invalid", "Invalid date (month)");
      } else {
        try {
          String dateCeiling = padString(Integer.toString(month + 1)) + date.substring(2);
          Date expirationDate = SimpleDate.parseShortDate(dateCeiling);
          if (expirationDate.before(new Date())) {
            errors.rejectValue("expirationDate", "creditCard.expiration.expired", "Card is expired");
          }
        } catch (ParseException e) {
          e.printStackTrace();
          errors.rejectValue("expirationDate", "creditCard.expiration.invalid", "Invalid date");
        }
      }
    }
  }

  // private static boolean checkExpDate(String date) {
  // if (!date.matches("\\d{4}")) {
  // return false;
  // } else {
  // int month = Integer.parseInt(date.substring(0, 2));
  // if (month < 1 || month > 12) {
  // return false;
  // } else {
  // try {
  // String dateCeiling = padString(Integer.toString(month + 1)) +
  // date.substring(2);
  // Date expirationDate = SimpleDate.parseShortDate(dateCeiling);
  // if (expirationDate.before(new Date())) {
  // return false;
  // }
  // } catch (ParseException e) {
  // e.printStackTrace();
  // return false;
  // }
  // }
  // }
  // return true;
  // }

  private static String padString(String value) {
    while (value.length() < 2) {
      value = "0" + value;
    }
    return value;
  }

  // public static void main(String[] args) {
  // String date;
  // boolean valid;
  // for (int month = 1; month <= 12; month++) {
  // for (int year = 11; year <= 18; year++) {
  // date = padString(Integer.toString(month)) + Integer.toString(year);
  // valid = checkExpDate(date);
  // }
  // }
  // }

  private void checkAddress(CreditCard creditCard, Errors errors) {
    Address address = TruConnectUtil.getAddress(creditCard);
    super.validate(address, errors);
    // if (creditCard.getAddress1() == null ||
    // creditCard.getAddress1().trim().isEmpty()
    // || creditCard.getAddress1().trim().length() < 5) {
    // errors.rejectValue("address1", "address.address1.required",
    // "You must enter an address");
    // }
    // if (creditCard.getCity() == null || creditCard.getCity().trim().isEmpty()
    // || creditCard.getCity().trim().length() < 2) {
    // errors.rejectValue("city", "address.city.required",
    // "You must enter a city");
    // } else if (!creditCard.getCity().trim().matches("\\D+")) {
    // errors.rejectValue("city", "address.city.invalid",
    // "A city cannot contain numeric characters");
    // }
    // if (creditCard.getState() == null ||
    // creditCard.getState().trim().equals("0")) {
    // errors.rejectValue("state", "address.state.required",
    // "You must specify a state");
    // }
    // if (creditCard.getZip() == null || creditCard.getZip().trim().isEmpty())
    // {
    // errors.rejectValue("zip", "address.zip.required",
    // "You must enter a zip code");
    // } else if (creditCard.getZip().trim().length() != 5 ||
    // !creditCard.getZip().trim().matches("\\d+")) {
    // errors.rejectValue("zip", "address.zip.invalid",
    // "You entered an invalid zip code");
    // }
  }

  private boolean mod10(String cardNumber) {
    int[] ar = new int[cardNumber.length()];
    String ccChar;
    int sum = 0;
    for (int i = 0; i < cardNumber.length(); ++i) {
      ccChar = String.valueOf(cardNumber.charAt(i));
      ar[i] = Integer.parseInt(ccChar);
    }
    for (int i = ar.length - 2; i >= 0; i -= 2) {
      ar[i] *= 2;
      if (ar[i] > 9)
        ar[i] -= 9;
    }
    for (int i = 0; i < ar.length; ++i) {
      sum += ar[i];
    }
    return sum % 10 == 0 ? true : false;
  }

}
