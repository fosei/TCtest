package com.trc.dao;

import java.util.Collection;

import com.trc.user.authority.Authority;

public interface AuthorityDaoModel {

  public void saveAuthority(Authority authority);

  public void deleteAuthority(Authority authority);

  public void updateAuthority(Authority authority);

  public Collection<Authority> getAuthoritiesWithRole(String role);

  public Collection<Authority> getAuthoritiesForUser(int userId);

}
