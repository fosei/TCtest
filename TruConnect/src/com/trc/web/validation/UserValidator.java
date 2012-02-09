package com.trc.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.manager.UserManager;
import com.trc.user.SecurityQuestionAnswer;
import com.trc.user.User;

@Component
public class UserValidator implements Validator {
  @Autowired
  private UserManager userManager;

  public static final int MAX_PASS_SIZE = 100;
  public static final int MIN_PASS_SIZE = 5;

  public static final int MAX_NAME_SIZE = 100;
  public static final int MIN_NAME_SIZE = 5;

  public static final int MAX_ANS_SIZE = 100;
  public static final int MIN_ANS_SIZE = 3;

  @Override
  public boolean supports(Class<?> myClass) {
    return User.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    checkUsername(user, errors);
    checkPassword(user.getPassword(), errors);
    checkEmail(user.getEmail(), errors);
    checkSecurityQuestion(user.getUserHint(), errors);
  }

  public void checkUsername(User user, Errors errors) {
    if (ValidationUtil.isEmpty(user.getUsername())) {
      errors.rejectValue("username", "username.required", "You must enter a username");
    } else if (!userManager.isUsernameAvailable(user.getUsername())) {
      String[] args = { user.getUsername() };
      errors.rejectValue("username", "username.unavailable", args, "Username is unavailable");
    } else if (!ValidationUtil.isBetween(user.getUsername(), MIN_NAME_SIZE, MAX_NAME_SIZE)) {
      errors.rejectValue("username", "username.size", "Your username must be 5 to 20 characters");
    } else if (!user.getUsername().equals(user.getEmail())) {
      errors.rejectValue("username", "username.invalid", "Username must match email");
    }
  }

  public void checkEmail(String email, Errors errors) {
    if (ValidationUtil.isEmpty(email)) {
      errors.rejectValue("email", "email.required", "You must enter an e-mail address");
    } else if (!EmailValidator.checkEmail(email)) {
      errors.rejectValue("email", "email.invalid", "E-mail address is invalid");
    } else if (!userManager.isEmailAvailable(email)) {
      String[] args = { email };
      errors.rejectValue("email", "email.unavailable", args, "E-mail address is unavailable");
    }
  }

  public static void checkPassword(String password, Errors errors) {
    if (ValidationUtil.isEmpty(password)) {
      errors.rejectValue("password", "password.required", "You must enter a password");
    } else if (!ValidationUtil.isBetween(password, MIN_PASS_SIZE, MAX_PASS_SIZE)) {
      errors.rejectValue("password", "password.size", "Your password must be " + MIN_PASS_SIZE + " to " + MAX_PASS_SIZE
          + " characters");
    } else if (!ValidationUtil.isAlphaNumeric(password)) {
      errors.rejectValue("password", "password.invalid", "Your password must be alphanumeric");
    }
  }

  public static void checkSecurityQuestion(SecurityQuestionAnswer securityQuestionAnswer, Errors errors) {
    int questionId = securityQuestionAnswer.getHintId();
    String questionAnswer = securityQuestionAnswer.getHintAnswer();
    if (questionId == 0) {
      errors.rejectValue("userHint.hintId", "securityQuestion.required", "You must select a question");
    } else if (ValidationUtil.isEmpty(questionAnswer)) {
      errors.rejectValue("userHint.hintAnswer", "securityQuestion.answer.required", "You must provide an answer");
    } else if (!ValidationUtil.isBetween(questionAnswer, MIN_ANS_SIZE, MAX_ANS_SIZE)) {
      errors.rejectValue("userHint.hintAnswer", "securityQuestion.answer.size",
          "Your answer must be at least 3 characters");
    }
  }

  public static void checkAuthorities(User user, Errors errors) {
    // TODO there are currently no rules on checking authorities. User's are
    // given default ROLE_USER when saved in UserManager.
  }
}
