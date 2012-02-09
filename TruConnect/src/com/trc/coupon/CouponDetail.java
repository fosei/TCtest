package com.trc.coupon;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.trc.coupon.contract.Contract;

/**
 * This object contains the duration of a Coupon and the Contract that it maps
 * to in Kenan.
 * 
 */

@Entity
@Table(name = "coupon_detail")
public class CouponDetail implements Serializable {
  private static final long serialVersionUID = -237740560474759272L;
  private int couponDetailId;
  private CouponDetailType detailType;
  private int duration;
  private String durationUnit;
  private Double amount;
  private Integer accountLimit;
  private Contract contract = new Contract();
  private Collection<Coupon> coupons = new HashSet<Coupon>();
  private Collection<CouponStackable> stackable = new HashSet<CouponStackable>();

  @Id
  @Column(name = "coupon_detail_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  public int getCouponDetailId() {
    return couponDetailId;
  }

  public void setCouponDetailId(int couponDetailId) {
    this.couponDetailId = couponDetailId;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "detail_type", nullable = false, insertable = false, updatable = false)
  public CouponDetailType getDetailType() {
    return detailType;
  }

  public void setDetailType(CouponDetailType detailType) {
    this.detailType = detailType;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "contract_type", nullable = false, insertable = false)
  public Contract getContract() {
    return contract;
  }

  public void setContract(Contract contract) {
    this.contract = contract;
  }

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "coupon_detail_id", nullable = false)
  public Collection<Coupon> getCoupons() {
    return coupons;
  }

  public void setCoupons(Collection<Coupon> coupons) {
    this.coupons = coupons;
  }

  @Column(name = "duration")
  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  @Column(name = "duration_unit")
  public String getDurationUnit() {
    return durationUnit;
  }

  public void setDurationUnit(String durationUnit) {
    this.durationUnit = durationUnit;
  }

  @Column(name = "amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  @Column(name = "account_limit")
  public Integer getAccountLimit() {
    return accountLimit;
  }

  public void setAccountLimit(Integer accountLimit) {
    this.accountLimit = accountLimit;
  }

  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "coupon_detail_id", nullable = false)
  public Collection<CouponStackable> getStackable() {
    return stackable;
  }

  public void setStackable(Collection<CouponStackable> stackable) {
    this.stackable = stackable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + accountLimit;
    result = prime * result + ((amount == null) ? 0 : amount.hashCode());
    result = prime * result + ((contract == null) ? 0 : contract.hashCode());
    result = prime * result + couponDetailId;
    result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
    result = prime * result + duration;
    result = prime * result + ((durationUnit == null) ? 0 : durationUnit.hashCode());
    result = prime * result + ((stackable == null) ? 0 : stackable.hashCode());
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
    CouponDetail other = (CouponDetail) obj;
    if (accountLimit != other.accountLimit)
      return false;
    if (amount == null) {
      if (other.amount != null)
        return false;
    } else if (!amount.equals(other.amount))
      return false;
    if (contract == null) {
      if (other.contract != null)
        return false;
    } else if (!contract.equals(other.contract))
      return false;
    if (couponDetailId != other.couponDetailId)
      return false;
    if (coupons == null) {
      if (other.coupons != null)
        return false;
    } else if (!coupons.equals(other.coupons))
      return false;
    if (duration != other.duration)
      return false;
    if (durationUnit == null) {
      if (other.durationUnit != null)
        return false;
    } else if (!durationUnit.equals(other.durationUnit))
      return false;
    if (stackable == null) {
      if (other.stackable != null)
        return false;
    } else if (!stackable.equals(other.stackable))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("couponDetailId=").append(couponDetailId).append(", ");
    sb.append("duration=").append(duration).append(", ");
    sb.append("durationUnit=").append(durationUnit).append(", ");
    sb.append("amount=").append(amount).append(", ");
    sb.append("contract=").append(contract.toString());
    sb.append("stackable=").append(stackable.toString());
    return sb.toString();
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--Coupon Detail--").append("\n");
    sb.append("  Coupon Detail ID=").append(couponDetailId).append("\n");
    sb.append("  Duration=").append(duration).append("\n");
    sb.append("  Duration Unit=").append(durationUnit).append("\n");
    sb.append("  Amount=").append(amount).append("\n");
    sb.append("  Contract=").append(contract.toString()).append("\n");
    sb.append("  Stackable=").append(stackable.toString());
    return sb.toString();
  }

}