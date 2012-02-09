package com.trc.dao;

import java.util.List;

import com.trc.user.security.SecurityQuestion;

public interface SecurityQuestionDaoModel {

  public List<SecurityQuestion> getSecurityQuestions();

  public SecurityQuestion getSecurityQuestion(int id);
}
