package com.trc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.trc.user.authority.Authority;

@Repository
@SuppressWarnings("unchecked")
public class AuthorityDao extends HibernateDaoSupport implements AuthorityDaoModel {

  @Autowired
  public void init(HibernateTemplate hibernateTemplate) {
    setHibernateTemplate(hibernateTemplate);
  }

  @Override
  public void saveAuthority(Authority authority) {
    getHibernateTemplate().save(authority);
  }

  @Override
  public void deleteAuthority(Authority authority) {
    getHibernateTemplate().delete(authority);
  }

  @Override
  public void updateAuthority(Authority authority) {
    getHibernateTemplate().update(authority);
  }

  @Override
  public List<Authority> getAuthoritiesWithRole(String role) {
    return getHibernateTemplate().find("from Authority authority where authority.authority = ?", role);
  }

  @Override
  public List<Authority> getAuthoritiesForUser(int userId) {
    return getHibernateTemplate().find("from Authority authority where authority.userId = ?", userId);
  }

}
