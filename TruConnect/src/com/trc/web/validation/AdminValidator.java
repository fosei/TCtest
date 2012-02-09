package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.trc.user.User;
import com.trc.util.ClassUtils;

@Component
public class AdminValidator extends UserValidator {

  @Override
  public boolean supports(Class<?> myClass) {
    return User.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;
    super.checkUsername(user, errors);
    super.checkPassword(user.getPassword(), errors);
    super.checkEmail(user.getEmail(), errors);
  }
}
