package com.trc.user;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class UserIdGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SessionImplementor arg0, Object arg1) throws HibernateException {
    // TODO Auto-generated method stub
    return null;
  }

}
