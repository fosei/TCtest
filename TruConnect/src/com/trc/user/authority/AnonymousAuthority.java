package com.trc.user.authority;

import com.trc.user.User;

public class AnonymousAuthority extends Authority {
  private static final long serialVersionUID = 1L;

  public AnonymousAuthority(User user) {
    setUser(user);
    setAuthority("ROLE_ANONYMOUS");
  }
}
