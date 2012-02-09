package com.trc.coupon;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CouponStackableId implements Serializable {
  private static final long serialVersionUID = 3695295907821624120L;
  private CouponDetail couponDetail;
  private int stackableCouponDetailId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "coupon_Detail_id", nullable = false, insertable = false, updatable = false)
  public CouponDetail getCouponDetail() {
    return couponDetail;
  }

  public void setCouponDetail(CouponDetail couponDetail) {
    this.couponDetail = couponDetail;
  }

  @Column(name = "stackable_with")
  public int getStackableCouponDetailId() {
    return stackableCouponDetailId;
  }

  public void setStackableCouponDetailId(int stackableCouponDetailId) {
    this.stackableCouponDetailId = stackableCouponDetailId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((couponDetail == null) ? 0 : couponDetail.hashCode());
    result = prime * result + stackableCouponDetailId;
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
    CouponStackableId other = (CouponStackableId) obj;
    if (couponDetail == null) {
      if (other.couponDetail != null)
        return false;
    } else if (!couponDetail.equals(other.couponDetail))
      return false;
    if (stackableCouponDetailId != other.stackableCouponDetailId)
      return false;
    return true;
  }

}
