package com.trc.coupon.contract;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.trc.coupon.CouponDetail;

/**
 * This object is the representation of a Contract in Kenan.
 * 
 */

@Entity
@Table(name = "coupon_contracts")
public class Contract implements Serializable {
  private static final long serialVersionUID = 2814309740931497593L;
  private int contractType;
  private String description;
  private Collection<CouponDetail> couponDetails = new HashSet<CouponDetail>();

  @Id
  @Column(name = "contract_type")
  public int getContractType() {
    return contractType;
  }

  public void setContractType(int contractType) {
    this.contractType = contractType;
  }

  @Column(name = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract")
  @Cascade(CascadeType.SAVE_UPDATE)
  public Collection<CouponDetail> getCoupons() {
    return couponDetails;
  }

  public void setCoupons(Collection<CouponDetail> couponDetails) {
    this.couponDetails = couponDetails;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + contractType;
    result = prime * result + ((couponDetails == null) ? 0 : couponDetails.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
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
    Contract other = (Contract) obj;
    if (contractType != other.contractType)
      return false;
    if (couponDetails == null) {
      if (other.couponDetails != null)
        return false;
    } else if (!couponDetails.equals(other.couponDetails))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Contract [contractType=" + contractType + ", description=" + description + "]";
  }

  @Transient
  public String toFormattedString() {
    StringBuffer sb = new StringBuffer();
    sb.append("--Contract--").append("\n");
    sb.append("  Contract Type=").append(contractType).append("\n");
    sb.append("  Description=").append(description);
    return sb.toString();
  }

}