package com.trc.user.contact;

import java.io.Serializable;

public class Address implements Serializable {
  private static final long serialVersionUID = 1L;
  private String label;
  private String encodedAddressId;
  private int addressId;
  private String address1;
  private String address2;
  private String address3;
  private String city;
  private String state;
  private String zip;
  private boolean isDefault;

  public boolean isDefault() {
    return isDefault;
  }

  public void setDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getEncodedAddressId() {
    return encodedAddressId;
  }

  public void setEncodedAddressId(String encodedAddressId) {
    this.encodedAddressId = encodedAddressId;
  }

  public int getAddressId() {
    return addressId;
  }

  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress3() {
    return address3;
  }

  public void setAddress3(String address3) {
    this.address3 = address3;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public boolean isEmpty() {
    return getAddress1() == null ? true : getAddress1().isEmpty();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
    result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
    result = prime * result + ((address3 == null) ? 0 : address3.hashCode());
    result = prime * result + addressId;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((encodedAddressId == null) ? 0 : encodedAddressId.hashCode());
    result = prime * result + (isDefault ? 1231 : 1237);
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
    Address other = (Address) obj;
    if (address1 == null) {
      if (other.address1 != null)
        return false;
    } else if (!address1.equals(other.address1))
      return false;
    if (address2 == null) {
      if (other.address2 != null)
        return false;
    } else if (!address2.equals(other.address2))
      return false;
    if (address3 == null) {
      if (other.address3 != null)
        return false;
    } else if (!address3.equals(other.address3))
      return false;
    if (addressId != other.addressId)
      return false;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (encodedAddressId == null) {
      if (other.encodedAddressId != null)
        return false;
    } else if (!encodedAddressId.equals(other.encodedAddressId))
      return false;
    if (isDefault != other.isDefault)
      return false;
    if (label == null) {
      if (other.label != null)
        return false;
    } else if (!label.equals(other.label))
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    return true;
  }

}