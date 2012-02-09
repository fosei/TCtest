package com.trc.coupon;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * This object represents a Coupon that refers to a Contract in Kenan.
 * 
 */

@Entity
@Table(name = "coupons")
public class Coupon implements Serializable {
  private static final long serialVersionUID = 3994060771315068342L;
  private int couponId;
  private String couponCode;
  private Date startDate;
  private Date endDate;
  private int quantity;
  private int used;
  private boolean enabled;
  private CouponDetail couponDetail = new CouponDetail();

  @Id
  @Column(name = "coupon_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getCouponId() {
    return couponId;
  }

  public void setCouponId(int couponId) {
    this.couponId = couponId;
  }

  @Column(name = "coupon_code")
  public String getCouponCode() {
    return couponCode;
  }

  public void setCouponCode(String couponCode) {
    this.couponCode = couponCode;
  }

  @Column(name = "date_start")
  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  @Column(name = "date_end")
  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Column(name = "enabled", columnDefinition = "BOOLEAN")
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Column(name = "quantity")
  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  @Column(name = "used")
  public int getUsed() {
    return used;
  }

  public void setUsed(int used) {
    this.used = used;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @Cascade(CascadeType.SAVE_UPDATE)
  @JoinColumn(name = "coupon_Detail_id", nullable = false, insertable = false, updatable = false)
  public CouponDetail getCouponDetail() {
    return couponDetail;
  }

  public void setCouponDetail(CouponDetail couponDetail) {
    this.couponDetail = couponDetail;
  }

  /* ****************************************************************
   * Helper Methods
   * ****************************************************************
   */

  @Transient
  public boolean isEmpty() {
    return couponCode == null || couponCode.isEmpty();
  }

  @Transient
  public boolean isContract() {
    return getCouponDetail().getContract().getContractType() > 0;
  }

  @Transient
  public boolean isPayment() {
    return getCouponDetail().getContract().getContractType() < 0;
  }

  @Transient
  public boolean isRecurring() {
    return getCouponDetail().getDuration() != 0 && getCouponDetail().getContract().getContractType() != -1
        && getCouponDetail().getAmount() == 0;
  }

  @Transient
  public boolean isStackable() {
    return getCouponDetail().getStackable().size() > 0;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("couponId=").append(couponId).append(", ");
    sb.append("couponCode=").append(couponCode).append(", ");
    sb.append("startDate=").append(startDate).append(", ");
    sb.append("endDate=").append(endDate).append(", ");
    sb.append("enabled=").append(enabled).append(", ");
    sb.append("used=").append(used).append(", ");
    sb.append("couponDetail=").append(couponDetail.toString());
    return sb.toString();
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--Coupon--").append("\n");
    sb.append("  Coupon ID=").append(couponId).append("\n");
    sb.append("  Coupon Code=").append(couponCode).append("\n");
    sb.append("  Start Date=").append(startDate).append("\n");
    sb.append("  End Date=").append(endDate).append("\n");
    sb.append("  Enabled=").append(enabled).append("\n");
    sb.append("  Used=").append(used).append("\n");
    sb.append("  Coupon Detail=").append(couponDetail.toString());
    return sb.toString();
  }

}