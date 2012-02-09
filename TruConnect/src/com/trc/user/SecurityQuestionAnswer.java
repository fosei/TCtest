package com.trc.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SecurityQuestionAnswer implements Serializable {
  private static final long serialVersionUID = 1L;
  private int hintId;
  private String hintAnswer;

  @Column(name = "hint_id")
  public int getHintId() {
    return hintId;
  }

  public void setHintId(int hintId) {
    this.hintId = hintId;
  }

  @Column(name = "hint_answer")
  public String getHintAnswer() {
    return hintAnswer;
  }

  public void setHintAnswer(String hintAnswer) {
    this.hintAnswer = hintAnswer;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("hintId=").append(hintId).append(", ").append("hintAnswer=").append(hintAnswer);
    return sb.toString();
  }
}