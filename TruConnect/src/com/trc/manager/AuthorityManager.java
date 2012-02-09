package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.dao.AuthorityDao;
import com.trc.user.authority.Authority;

@Service
public class AuthorityManager implements AuthorityManagerModel {
  @Autowired
  private AuthorityDao authorityDao;

  @Override
  public void saveAuthority(Authority authority) {
    authorityDao.saveAuthority(authority);
  }

  @Override
  public void deleteAuthority(Authority authority) {
    authorityDao.deleteAuthority(authority);
  }

  @Override
  public void updateAuthority(Authority authority) {
    authorityDao.updateAuthority(authority);
  }

  @Override
  public List<Authority> getAuthoritiesWithRole(String role) {
    return authorityDao.getAuthoritiesWithRole(role);
  }

  @Override
  public List<Authority> getAuthoritiesForUser(int userId) {
    return authorityDao.getAuthoritiesForUser(userId);
  }

}
