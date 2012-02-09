package com.trc.web.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.tscp.mvne.Account;

@Component
public class AccountValidator implements Validator {

  @Override
  public boolean supports(Class<?> myClass) {
    return Account.class.isAssignableFrom(myClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Account account = (Account) target;
    checkAccountNumber(account.getAccountno(), errors);
  }

  public void checkAccountNumber(int accountNumber, Errors errors) {
    if (accountNumber < 1) {
      errors.rejectValue("accountno", "account.number.requred", "You must choose an account");
    }
  }
  //
  // @Deprecated
  // public void validateChooseAccount(Account account, ValidationContext
  // context) {
  // MessageContext messages = context.getMessageContext();
  // if (account.getAccountno() == 0) {
  // WebFlowUtil.addError(messages, "accountno", "required.accountno.Account",
  // "You must choose an account");
  // }
  // }

}
