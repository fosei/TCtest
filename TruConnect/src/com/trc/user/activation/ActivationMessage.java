package com.trc.user.activation;

import java.io.Serializable;

public class ActivationMessage implements Serializable {
  private static final long serialVersionUID = -6410150457354168388L;
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
}
