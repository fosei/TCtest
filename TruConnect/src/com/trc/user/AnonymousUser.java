package com.trc.user;

import com.trc.user.authority.AnonymousAuthority;
import com.trc.web.session.SessionManager;

public class AnonymousUser extends User {
  private static final long serialVersionUID = 1L;

  public AnonymousUser() {
    setUserId(0);
    getRoles().add(new AnonymousAuthority(this));
    setUsername(SessionManager.getCurrentSessionId());
  }
}
