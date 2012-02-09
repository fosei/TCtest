package com.trc.coupon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class UserCouponId implements Serializable {
  private static final long serialVersionUID = 8178840371647916866L;
  private int userId;
  private Coupon coupon = new Coupon();
  private int accountNumber;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coupon_id", nullable = false, insertable = false)
  public Coupon getCoupon() {
    return this.coupon;
  }

  public void setCoupon(Coupon coupon) {
    this.coupon = coupon;
  }

  @Column(name = "user_id")
  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Column(name = "account_number")
  public int getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + accountNumber;
    result = prime * result + ((coupon == null) ? 0 : coupon.hashCode());
    result = prime * result + userId;
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
    UserCouponId other = (UserCouponId) obj;
    if (accountNumber != other.accountNumber)
      return false;
    if (coupon == null) {
      if (other.coupon != null)
        return false;
    } else if (!coupon.equals(other.coupon))
      return false;
    if (userId != other.userId)
      return false;
    return true;
  }

}
