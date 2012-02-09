package com.trc.user.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trc.user.User;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
public class Authority implements Serializable {
  private static final long serialVersionUID = 1L;
  private User user;
  private String authority;

  public Authority() {
    // default
  }

  public Authority(User user, String authority) {
    setUser(user);
    setAuthority(authority);
  }

  @Id
  @Column(name = "authority")
  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }

  @Id
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, insertable = false)
  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("userId=").append(getUser().getUserId()).append(", ");
    sb.append("authority=").append(getAuthority());
    return sb.toString();
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--Authority--").append("\n");
    sb.append("  User ID=").append(getUser().getUserId()).append("\n");
    sb.append("  Authority=").append(getAuthority());
    return sb.toString();
  }
}