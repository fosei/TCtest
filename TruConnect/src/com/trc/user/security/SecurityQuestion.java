package com.trc.user.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hints")
public class SecurityQuestion implements Serializable {
  private static final long serialVersionUID = 1L;
  private int hintId;
  private String hintQuestion;

  @Id
  @Column(name = "hint_id")
  public int getHintId() {
    return hintId;
  }

  public void setHintId(int hintId) {
    this.hintId = hintId;
  }

  @Column(name = "question")
  public String getHintQuestion() {
    return hintQuestion;
  }

  public void setHintQuestion(String hintQuestion) {
    this.hintQuestion = hintQuestion;
  }
}
