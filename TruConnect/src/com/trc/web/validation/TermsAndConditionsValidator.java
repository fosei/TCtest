package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.trc.user.activation.TermsAndConditions;

@Component
public class TermsAndConditionsValidator implements Validator {

  @Override
  public boolean supports(Class<?> myClass) {
    return TermsAndConditions.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    TermsAndConditions termsAndConditions = (TermsAndConditions) target;
    checkAcceptTerms(termsAndConditions, errors);
  }

  private void checkAcceptTerms(TermsAndConditions termsAndConditions, Errors errors) {
    if (!termsAndConditions.isAccept()) {
      errors.rejectValue("accept", "terms.required", "You must accept the terms");
    }
  }
}
