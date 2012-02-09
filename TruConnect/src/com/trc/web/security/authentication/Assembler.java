package com.trc.web.security.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import com.trc.user.authority.Authority;

@Deprecated
public class Assembler {

  @Transactional(readOnly = true)
  public User buildUserFromUser(com.trc.user.User myUser) {
    String username = myUser.getUsername();
    String password = myUser.getPassword();
    boolean enabled = myUser.isEnabled();
    boolean accountNonExpired = enabled;
    boolean credentialsNonExpired = enabled;
    boolean accountNonLocked = enabled;

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (Authority auth : myUser.getRoles()) {
      authorities.add(new GrantedAuthorityImpl(auth.getAuthority()));
    }

    User springUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        authorities);
    return springUser;
  }
}