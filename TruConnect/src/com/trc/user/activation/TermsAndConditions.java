package com.trc.user.activation;

import java.io.Serializable;

public class TermsAndConditions implements Serializable {
  private static final long serialVersionUID = 1L;
  private boolean accept;

  public boolean isAccept() {
    return accept;
  }

  public void setAccept(boolean accept) {
    this.accept = accept;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (accept ? 1231 : 1237);
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
    TermsAndConditions other = (TermsAndConditions) obj;
    if (accept != other.accept)
      return false;
    return true;
  }

}
