package com.trc.user.activation;

import java.io.Serializable;

import com.trc.security.encryption.Md5Encoder;
import com.trc.user.User;

public class Registration implements Serializable {
  private static final long serialVersionUID = 1L;
  private User user = new User();
  private String confirmPassword;
  private String confirmEmail;
  private String jcaptcha;

  // private TermsAndConditions termsAndConditions = new TermsAndConditions();

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User newUser) {
    this.user = newUser;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  public String getConfirmEmail() {
    return confirmEmail;
  }

  public void setConfirmEmail(String confirmEmail) {
    this.confirmEmail = confirmEmail;
  }

  public String getJcaptcha() {
    return jcaptcha;
  }

  public void setJcaptcha(String jcaptcha) {
    this.jcaptcha = jcaptcha;
  }

  // public TermsAndConditions getTermsAndConditions() {
  // return termsAndConditions;
  // }

  // public void setTermsAndConditions(TermsAndConditions termsAndConditions) {
  // this.termsAndConditions = termsAndConditions;
  // }

  public void encodePassword() {
    String md5Password = Md5Encoder.encode(getUser().getPassword());
    getUser().setPassword(md5Password);
    setConfirmPassword(md5Password);
  }

  /* *******************************************************************
   * Messages to be displayed during processing
   * *******************************************************************
   */

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void setMessageCreateBilling() {
    setMessage("Building your billing account...");
  }

  public void setMessageTestingActivation() {
    setMessage("Testing your device...");
  }

  public void setMessagePayment() {
    setMessage("Processing your payment...");
  }

  public void setMessageActivation() {
    setMessage("Activating your device...");
  }

  public void setMessageCreateService() {
    setMessage("Activating your services...");
  }

  public void setMessageEnableUser() {
    setMessage("Enabling your online profile...");
  }

  public void setMessageSaveUser() {
    setMessage("Finalizing your online profile...");
  }

  /* *******************************************************************
   * Helper Methods
   * *******************************************************************
   */

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((confirmEmail == null) ? 0 : confirmEmail.hashCode());
    result = prime * result + ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
    result = prime * result + ((jcaptcha == null) ? 0 : jcaptcha.hashCode());
    result = prime * result + ((message == null) ? 0 : message.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
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
    Registration other = (Registration) obj;
    if (confirmEmail == null) {
      if (other.confirmEmail != null)
        return false;
    } else if (!confirmEmail.equals(other.confirmEmail))
      return false;
    if (confirmPassword == null) {
      if (other.confirmPassword != null)
        return false;
    } else if (!confirmPassword.equals(other.confirmPassword))
      return false;
    if (jcaptcha == null) {
      if (other.jcaptcha != null)
        return false;
    } else if (!jcaptcha.equals(other.jcaptcha))
      return false;
    if (message == null) {
      if (other.message != null)
        return false;
    } else if (!message.equals(other.message))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

}
