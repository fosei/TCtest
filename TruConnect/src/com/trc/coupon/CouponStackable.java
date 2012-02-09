package com.trc.coupon;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "coupon_stackable")
public class CouponStackable implements Serializable {
  private static final long serialVersionUID = 6585045104574505976L;
  private CouponStackableId id;

  @EmbeddedId
  public CouponStackableId getId() {
    return id;
  }

  public void setId(CouponStackableId id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return id.getCouponDetail().getCouponDetailId() + " stackable with " + id.getStackableCouponDetailId();
  }

}