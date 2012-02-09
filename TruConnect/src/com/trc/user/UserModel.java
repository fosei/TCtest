package com.trc.user;

import java.util.Collection;
import java.util.Date;

import com.trc.user.authority.Authority;
import com.trc.user.contact.ContactInfo;

public interface UserModel {

  public int getUserId();

  public void setUserId(int userId);

  public String getUsername();

  public void setUsername(String username);

  public String getPassword();

  public void setPassword(String password);

  public boolean isEnabled();

  public void setEnabled(boolean enabled);

  public Date getDateEnabled();

  public void setDateEnabled(Date date);

  public Date getDateDisabled();

  public void setDateDisabled(Date date);

  public SecurityQuestionAnswer getUserHint();

  public void setUserHint(SecurityQuestionAnswer securityQuestionAnswer);

  public Collection<Authority> getRoles();

  public void setRoles(Collection<Authority> roles);

  public ContactInfo getContactInfo();

  public void setContactInfo(ContactInfo contactInfo);
}