package com.trc.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.service.AddressServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.tscp.mvne.Account;
import com.tscp.mvne.CustAcctMapDAO;
import com.tscp.mvne.CustAddress;
import com.tscp.mvne.Customer;
import com.tscp.mvne.TruConnect;

@Service
public class AddressService implements AddressServiceModel {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  private List<Address> getAddressFromUserDetails(User user) {
    List<Address> addressList = new ArrayList<Address>();
    addressList.add(user.getContactInfo().getAddress());
    return addressList;
  }

  private boolean isNewActivation(User user) {
    return !user.getContactInfo().getAddress().isEmpty();
  }

  private List<Address> getAllAddressesFromAccounts(User user) throws AddressServiceException {
    List<Address> addressList = new ArrayList<Address>();
    Customer customer = TruConnectUtil.toCustomer(user);
    try {
      Account account;
      Address address;
      for (CustAcctMapDAO accountMap : truConnect.getCustomerAccounts(customer.getId())) {
        account = truConnect.getAccountInfo(accountMap.getAccountNo());
        address = new Address();
        address.setAddress1(account.getContactAddress1());
        address.setAddress2(account.getContactAddress2());
        address.setCity(account.getContactCity());
        address.setState(account.getContactState());
        address.setZip(account.getContactZip());
        addressList.add(address);
      }
      return addressList;
    } catch (WebServiceException e) {
      throw new AddressServiceException(e.getMessage(), e.getCause());
    }
  }

  private List<Address> getAllAddresses(Customer customer) throws AddressServiceException {
    List<Address> addressList = new ArrayList<Address>();
    try {
      List<CustAddress> custAddressList = truConnect.getCustAddressList(customer, 0);
      if (custAddressList != null) {
        for (CustAddress custAddress : custAddressList) {
          addressList.add(TruConnectUtil.toAddress(custAddress));
        }
      }
      return addressList;
    } catch (WebServiceException e) {
      throw new AddressServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<Address> getAllAddresses(User user) throws AddressServiceException {
    List<Address> addressList = new ArrayList<Address>();
    Customer customer = TruConnectUtil.toCustomer(user);
    try {
      if (isNewActivation(user)) {
        return getAddressFromUserDetails(user);
      } else {
        addressList = getAllAddresses(customer);
        return addressList.isEmpty() ? getAllAddressesFromAccounts(user) : addressList;
      }
    } catch (AddressServiceException e) {
      throw e;
    }
  }

  @Override
  public Address getAddress(User user, int addressId) throws AddressServiceException {
    List<Address> addressList = new ArrayList<Address>();
    try {
      addressList = getAllAddresses(user);
      for (Address address : addressList) {
        if (addressId == address.getAddressId())
          return address;
      }
      return new Address();
    } catch (AddressServiceException e) {
      throw new AddressServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void addAddress(User user, Address address) throws AddressServiceException {
    Customer customer = TruConnectUtil.toCustomer(user);
    CustAddress custAddress = TruConnectUtil.toCustAddress(user, address);
    try {
      truConnect.addCustAddress(customer, custAddress);
    } catch (WebServiceException e) {
      throw new AddressServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<Address> removeAddress(User user, Address address) throws AddressServiceException {
    Customer customer = TruConnectUtil.toCustomer(user);
    CustAddress custAddress = TruConnectUtil.toCustAddress(user, address);
    try {
      return TruConnectUtil.toAddressList(truConnect.deleteCustAddress(customer, custAddress));
    } catch (WebServiceException e) {
      throw new AddressServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<Address> updateAddress(User user, Address address) throws AddressServiceException {
    Customer customer = TruConnectUtil.toCustomer(user);
    CustAddress custAddress = TruConnectUtil.toCustAddress(user, address);
    try {
      return TruConnectUtil.toAddressList(truConnect.updateCustAddress(customer, custAddress));
    } catch (WebServiceException e) {
      throw new AddressServiceException(e.getMessage());
    }
  }

}
