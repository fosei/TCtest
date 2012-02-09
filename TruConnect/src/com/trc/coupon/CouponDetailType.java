package com.trc.coupon;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "coupon_detail_type")
public class CouponDetailType implements Serializable {
  private static final long serialVersionUID = -1326869650430552232L;
  private int detailType;
  private String description;
  private Collection<CouponDetail> couponDetails;

  @Id
  @Column(name = "detail_type")
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getDetailType() {
    return detailType;
  }

  public void setDetailType(int detailType) {
    this.detailType = detailType;
  }

  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "detail_type", nullable = false)
  public Collection<CouponDetail> getCouponDetails() {
    return couponDetails;
  }

  public void setCouponDetails(Collection<CouponDetail> couponDetails) {
    this.couponDetails = couponDetails;
  }

}
