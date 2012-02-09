package com.trc.web.validation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.user.User;
import com.trc.user.activation.Registration;
import com.trc.user.contact.ContactInfo;

@Component
public class RegistrationValidator extends UserValidator {
  @Autowired
  private HttpServletRequest request;

  @Override
  public boolean supports(Class<?> myClass) {
    return Registration.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Registration registration = (Registration) target;
    User user = registration.getUser();
    checkName(user.getContactInfo(), errors);
    checkUser(user, errors);
    checkConfirmEmail(user.getEmail(), registration.getConfirmEmail(), errors);
    checkConfirmPassword(user.getPassword(), registration.getConfirmPassword(), errors);
  }

  /* ****************************************************************
   * WebFlow state validations
   * ****************************************************************
   */

  public void validateRequestUserInfo(Registration registration, Errors errors) {
    checkJCaptcha(registration, errors);
  }

  // public void validateShowPlansAndTerms(Registration registration, Errors
  // errors) {
  // checkAcceptTerms(registration, errors);
  // }

  /* ****************************************************************
   * Property validations
   * ****************************************************************
   */

  private void checkName(ContactInfo contactInfo, Errors errors) {
    errors.pushNestedPath("user");
    errors.pushNestedPath("contactInfo");
    ContactInfoValidator.checkName(contactInfo, errors);
    errors.popNestedPath();
    errors.popNestedPath();
  }

  private void checkUser(User user, Errors errors) {
    errors.pushNestedPath("user");
    super.validate(user, errors);
    errors.popNestedPath();
  }

  private void checkConfirmEmail(String email, String confirmEmail, Errors errors) {
    if (!ValidationUtil.matches(email, confirmEmail)) {
      errors.rejectValue("confirmEmail", "email.mismatch", "Your e-mails do not match");
    }
  }

  private void checkConfirmPassword(String password, String confirmPassword, Errors errors) {
    if (!ValidationUtil.matches(password, confirmPassword)) {
      errors.rejectValue("confirmPassword", "password.mismatch", "Your passwords do not match");
    }
  }

  // private void checkAcceptTerms(Registration registration, Errors errors) {
  // errors.pushNestedPath("termsAndConditions");
  // if (!registration.getTermsAndConditions().isAccept()) {
  // errors.rejectValue("accept", "terms.required",
  // "You must accept the terms");
  // }
  // errors.popNestedPath();
  // }

  private void checkJCaptcha(Registration registration, Errors errors) {
    JcaptchaValidator.validate(request, errors);
    if (errors.hasErrors()) {
      registration.setJcaptcha(null);
    }
  }

}
