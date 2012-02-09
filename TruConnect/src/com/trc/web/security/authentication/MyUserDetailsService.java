package com.trc.web.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.trc.manager.UserManager;
import com.trc.user.User;

//TODO reimplement this service with assembler to keep Spring model seperated from our internal model
public class MyUserDetailsService implements UserDetailsService {
  @Autowired
  private UserManager userManager;

  // @Autowired
  // private Assembler assembler;

  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
    User user = userManager.getUserByUsername(username);
    user = user != null ? user : userManager.getUserByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException("User: " + username + " could not be found.");
    }
    return user;
    // return assembler.buildUserFromUser(user);
  }

  public UserManager getUserManager() {
    return userManager;
  }

  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  // public Assembler getAssembler() {
  // return assembler;
  // }
  //
  // public void setAssembler(Assembler assembler) {
  // this.assembler = assembler;
  // }
}