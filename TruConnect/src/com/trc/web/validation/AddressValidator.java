package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.user.contact.Address;

@Component
public class AddressValidator implements Validator {
  public static final int MIN_ADD_SIZE = 5;
  public static final int MIN_CITY_SIZE = 2;
  public static final int ZIP_SIZE = 5;

  @Override
  public boolean supports(Class<?> myClass) {
    return Address.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Address address = (Address) target;
    checkAddress1(address.getAddress1(), errors);
    checkCity(address.getCity(), errors);
    checkState(address.getState(), errors);
    checkZip(address.getZip(), errors);
  }

  public static void checkAddress1(String address1, Errors errors) {
    if (ValidationUtil.isEmpty(address1) || !ValidationUtil.isBetween(address1, MIN_ADD_SIZE, 100)) {
      errors.rejectValue("address1", "address.address1.required", "You must enter an address");
    }
  }

  public static void checkAddress2(String address2, Errors errors) {
    // TODO there are currently no rules for address 2
  }

  public static void checkCity(String city, Errors errors) {
    if (ValidationUtil.isEmpty(city) || !ValidationUtil.isBetween(city, MIN_CITY_SIZE, 100)) {
      errors.rejectValue("city", "address.city.required", "You must enter a city");
    } else if (!ValidationUtil.isAlpha(city)) {
      errors.rejectValue("city", "address.city.invalid", "A city cannot contain numeric characters");
    }
  }

  public static void checkState(String state, Errors errors) {
    if (state.trim().equals("0")) {
      errors.rejectValue("state", "address.state.required", "You must specify a state");
    }
  }

  public static void checkZip(String zipCode, Errors errors) {
    if (ValidationUtil.isEmpty(zipCode)) {
      errors.rejectValue("zip", "address.zip.required", "You must enter a zip code");
    } else if (zipCode.trim().length() != ZIP_SIZE || !ValidationUtil.isNumeric(zipCode)) {
      errors.rejectValue("zip", "address.zip.invalid", "You entered an invalid zip code");
    }
  }
}