package com.trc.manager;

import java.util.List;

import com.trc.user.security.SecurityQuestion;

public interface SecurityQuestionManagerModel {

  public List<SecurityQuestion> getSecurityQuestions();

  public SecurityQuestion getSecurityQuestion(int id);
}
