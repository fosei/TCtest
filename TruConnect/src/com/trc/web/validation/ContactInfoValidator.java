package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.user.contact.Address;
import com.trc.user.contact.ContactInfo;

@Component
public class ContactInfoValidator extends AddressValidator {

  @Override
  public boolean supports(Class<?> myClass) {
    return ContactInfo.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ContactInfo contactInfo = (ContactInfo) target;
    checkName(contactInfo, errors);
    checkPhoneNumber(contactInfo.getPhoneNumber(), errors);
    checkAddress(contactInfo.getAddress(), errors);
  }

  public static void checkName(ContactInfo contactInfo, Errors errors) {
    checkFirstName(contactInfo.getFirstName(), errors);
    checkLastName(contactInfo.getLastName(), errors);
  }

  public static void checkFirstName(String firstName, Errors errors) {
    if (ValidationUtil.isEmpty(firstName)) {
      errors.rejectValue("firstName", "contact.firstName.required", "You must enter a first name");
    }
  }

  public static void checkLastName(String lastName, Errors errors) {
    if (ValidationUtil.isEmpty(lastName)) {
      errors.rejectValue("lastName", "contact.lastName.required", "You must enter a last name");
    }
  }

  public static void checkPhoneNumber(String phoneNumber, Errors errors) {
    if (ValidationUtil.isEmpty(phoneNumber)) {
      errors.rejectValue("phoneNumber", "contact.phoneNumber.required", "You must enter a phone number");
    } else if (phoneNumber.trim().length() != 10) {
      errors.rejectValue("phoneNumber", "contact.phoneNumber.size", "Phone number must be 10 digits long");
    } else if (!ValidationUtil.isNumeric(phoneNumber)) {
      errors.rejectValue("phoneNumber", "contact.phoneNumber.invalid", "Phone number must be numeric only");
    }
  }

  public void checkAddress(Address address, Errors errors) {
    errors.pushNestedPath("address");
    super.validate(address, errors);
    errors.popNestedPath();
  }
}