package com.trc.service;

import java.util.List;

import com.trc.exception.service.AddressServiceException;
import com.trc.user.User;
import com.trc.user.contact.Address;

public interface AddressServiceModel {

  public List<Address> getAllAddresses(User user) throws AddressServiceException;

  public Address getAddress(User user, int addressId) throws AddressServiceException;

  public void addAddress(User user, Address address) throws AddressServiceException;

  public List<Address> removeAddress(User user, Address address) throws AddressServiceException;

  public List<Address> updateAddress(User user, Address address) throws AddressServiceException;

}
