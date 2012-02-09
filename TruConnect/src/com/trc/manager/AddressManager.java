package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.management.AddressManagementException;
import com.trc.exception.service.AddressServiceException;
import com.trc.service.AddressService;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.session.cache.CacheKey;
import com.trc.web.session.cache.CacheManager;

@Component
public class AddressManager implements AddressManagerModel {
  @Autowired
  private AddressService addressService;

  public Address getDefaultAddress(User user) throws AddressManagementException {
    try {
      List<Address> addressList = getAllAddresses(user);
      if (addressList != null && !addressList.isEmpty()) {
        return addressList.get(0);
      } else {
        return null;
      }
    } catch (AddressManagementException e) {
      throw e;
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<Address> getAllAddresses(User user) throws AddressManagementException {
    List<Address> addressList = getAllAddressesFromCache();
    if (addressList == null) {
      try {
        addressList = addressService.getAllAddresses(user);
        CacheManager.set(CacheKey.ADDRESSES, addressList);
      } catch (AddressServiceException e) {
        throw new AddressManagementException(e.getMessage(), e.getCause());
      }
    }
    return addressList;
  }

  @Override
  public Address getAddress(User user, int addressId) throws AddressManagementException {
    Address address = getAddressFromCache(addressId);
    try {
      return address == null ? addressService.getAddress(user, addressId) : address;
    } catch (AddressServiceException e) {
      throw new AddressManagementException(e.getMessage(), e.getCause());
    }
  }

  @SuppressWarnings("unchecked")
  private List<Address> getAllAddressesFromCache() {
    return (List<Address>) CacheManager.get(CacheKey.ADDRESSES);
  }

  private Address getAddressFromCache(int addressId) {
    List<Address> addressList = getAllAddressesFromCache();
    if (addressList != null) {
      for (Address address : addressList) {
        if (address.getAddressId() == addressId)
          return address;
      }
    }
    return null;
  }

  @Override
  public void addAddress(User user, Address address) throws AddressManagementException {
    try {
      addressService.addAddress(user, address);
      CacheManager.clear(CacheKey.ADDRESSES);
    } catch (AddressServiceException e) {
      throw new AddressManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<Address> removeAddress(User user, Address address) throws AddressManagementException {
    try {
      List<Address> addressList = addressService.getAllAddresses(user);
      if (addressList.size() > 1) {
        addressList = addressService.removeAddress(user, address);
      }
      CacheManager.clear(CacheKey.ADDRESSES);
      return addressList;
    } catch (AddressServiceException e) {
      throw new AddressManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<Address> updateAddress(User user, Address address) throws AddressManagementException {
    try {
      List<Address> addressList = addressService.updateAddress(user, address);
      CacheManager.clear(CacheKey.ADDRESSES);
      return addressList;
    } catch (AddressServiceException e) {
      throw new AddressManagementException(e.getMessage(), e.getCause());
    }

  }

}
