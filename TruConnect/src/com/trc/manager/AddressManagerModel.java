package com.trc.manager;

import java.util.List;

import com.trc.exception.management.AddressManagementException;
import com.trc.user.User;
import com.trc.user.contact.Address;

public interface AddressManagerModel {

  public List<Address> getAllAddresses(User user) throws AddressManagementException;

  public Address getAddress(User user, int addressId) throws AddressManagementException;

  public void addAddress(User user, Address address) throws AddressManagementException;

  public List<Address> removeAddress(User user, Address address) throws AddressManagementException;

  public List<Address> updateAddress(User user, Address address) throws AddressManagementException;
}
