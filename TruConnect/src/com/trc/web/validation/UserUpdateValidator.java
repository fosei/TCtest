package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.user.User;
import com.trc.user.security.UpdateEmail;
import com.trc.user.security.UpdatePassword;
import com.trc.user.security.UpdateUser;

@Component
public class UserUpdateValidator extends UserValidator {

  @Override
  public boolean supports(Class<?> myClass) {
    return UpdateUser.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    // this should not be called
  }

  public void validateNewPassword(UpdatePassword updatePassword, Errors errors) {
    String newPassword = updatePassword.getPassword();
    super.checkPassword(newPassword, errors);
    if (!ValidationUtil.matches(newPassword, updatePassword.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "password.mismatch", "Your passwords do not match");
    }
  }

  public void validateNewEmail(UpdateEmail updateEmail, Errors errors) {
    String newEmail = updateEmail.getEmail();
    super.checkEmail(newEmail, errors);
    if (!ValidationUtil.matches(updateEmail.getEmail(), updateEmail.getConfirmEmail())) {
      errors.rejectValue("confirmEmail", "email.mismatch", "Your e-mails do not match");
    }
  }

  public void validatePasswordChange(UpdatePassword updatePassword, Errors errors, User user) {
    if (!ValidationUtil.isCorrectPassword(updatePassword.getOldPassword(), user.getPassword())) {
      errors.rejectValue("oldPassword", "password.incorrect", "Incorrect Password");
    }
    validateNewPassword(updatePassword, errors);
  }

  public void validateEmailChange(UpdateEmail updateEmail, Errors errors, User user) {
    if (!ValidationUtil.isCorrectPassword(updateEmail.getOldPassword(), user.getPassword())) {
      errors.rejectValue("oldPassword", "password.incorrect", "Incorrect Password");
    }
    validateNewEmail(updateEmail, errors);
  }

}