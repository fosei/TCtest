package com.trc.coupon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trc.user.User;
import com.tscp.mvne.Account;

/**
 * This object represents a coupon that has been applied to a user. Only users
 * that have a coupon applied to the specified account will have a UserCoupon
 * object backing them.
 * 
 */

@Entity
@Table(name = "coupon_user_map")
public class UserCoupon implements Serializable {
  private static final long serialVersionUID = -3140555927402030377L;
  private UserCouponId id = new UserCouponId();
  private int kenanContractId;
  private boolean active = true;

  /* *****************************************************************
   * Constructors
   * *****************************************************************
   */

  public UserCoupon() {
    // do nothing
  }

  public UserCoupon(Coupon coupon, User user, Account account) {
    coupon = coupon == null ? new Coupon() : coupon;
    user = user == null ? new User() : user;
    account = account == null ? new Account() : account;
    this.getId().setUserId(user.getUserId());
    this.getId().setCoupon(coupon);
    this.getId().setAccountNumber(account.getAccountno());
  }

  /* *****************************************************************
   * Setters and Getters
   * *****************************************************************
   */

  @EmbeddedId
  public UserCouponId getId() {
    return id;
  }

  public void setId(UserCouponId id) {
    this.id = id;
  }

  @Column(name = "kenan_contract_id")
  public int getKenanContractId() {
    return kenanContractId;
  }

  public void setKenanContractId(int kenanContractId) {
    this.kenanContractId = kenanContractId;
  }

  @Column(name = "active", columnDefinition = "BOOLEAN")
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  /* *****************************************************************
   * Helper Methods
   * *****************************************************************
   */

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("userId=").append(getId().getUserId()).append(", ");
    sb.append("coupon=").append(getId().getCoupon().toString()).append(", ");
    sb.append("accountNumber=").append(getId().getAccountNumber()).append(", ");
    sb.append("kenanContractid=").append(kenanContractId);
    return sb.toString();
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--User Coupon--").append("\n");
    sb.append("  User ID=").append(getId().getUserId()).append("\n");
    sb.append("  Coupon=").append(getId().getCoupon().toString()).append("\n");
    sb.append("  Account Number=").append(getId().getAccountNumber()).append("\n");
    sb.append("  Kenan Contract ID=").append(kenanContractId);
    return sb.toString();
  }

}
