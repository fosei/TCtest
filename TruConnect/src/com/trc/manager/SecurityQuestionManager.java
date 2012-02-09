package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.dao.SecurityQuestionDao;
import com.trc.user.security.SecurityQuestion;

@Service
public class SecurityQuestionManager implements SecurityQuestionManagerModel {
  private SecurityQuestionDao securityQuestionDao;

  @Autowired
  public void init(SecurityQuestionDao securityQuestionDao) {
    this.securityQuestionDao = securityQuestionDao;
  }

  @Override
  public List<SecurityQuestion> getSecurityQuestions() {
    return securityQuestionDao.getSecurityQuestions();
  }

  @Override
  public SecurityQuestion getSecurityQuestion(int id) {
    return securityQuestionDao.getSecurityQuestion(id);
  }

}
