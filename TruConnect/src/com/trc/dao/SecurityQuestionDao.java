package com.trc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.user.security.SecurityQuestion;

@Repository
@SuppressWarnings("unchecked")
public class SecurityQuestionDao extends HibernateDaoSupport implements SecurityQuestionDaoModel {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public List<SecurityQuestion> getSecurityQuestions() {
    return getHibernateTemplate().find("from SecurityQuestion");
  }

  @Override
  public SecurityQuestion getSecurityQuestion(int id) {
    return getHibernateTemplate().get(SecurityQuestion.class, id);
  }

}
