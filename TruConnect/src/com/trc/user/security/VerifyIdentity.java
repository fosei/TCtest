package com.trc.user.security;

import java.io.Serializable;

public class VerifyIdentity implements Serializable {
  private static final long serialVersionUID = 1L;
  private String email;
  private String hintAnswer;

  public String getHintAnswer() {
    return hintAnswer;
  }

  public void setHintAnswer(String hintAnswer) {
    this.hintAnswer = hintAnswer;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    VerifyIdentity other = (VerifyIdentity) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    return true;
  }

}
