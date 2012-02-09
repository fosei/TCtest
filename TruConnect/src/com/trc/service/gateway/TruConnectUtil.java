package com.trc.service.gateway;

import java.util.ArrayList;
import java.util.List;

import com.trc.user.User;
import com.trc.user.contact.Address;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.CustAddress;
import com.tscp.mvne.Customer;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

public final class TruConnectUtil {

  /* ***********************************************************************
   * Copying Methods
   * ***********************************************************************
   */

  public static final void copyCreditCard(CreditCard outCreditCard, CreditCard inCreditCard) {
    outCreditCard.setAddress1(inCreditCard.getAddress1());
    outCreditCard.setAddress2(inCreditCard.getAddress2());
    outCreditCard.setAlias(inCreditCard.getAlias());
    outCreditCard.setCity(inCreditCard.getCity());
    outCreditCard.setCreditCardNumber(inCreditCard.getCreditCardNumber());
    outCreditCard.setExpirationDate(inCreditCard.getExpirationDate());
    outCreditCard.setIsDefault(inCreditCard.getIsDefault());
    outCreditCard.setNameOnCreditCard(inCreditCard.getNameOnCreditCard());
    outCreditCard.setPaymentid(inCreditCard.getPaymentid());
    outCreditCard.setState(inCreditCard.getState());
    outCreditCard.setVerificationcode(inCreditCard.getVerificationcode());
    outCreditCard.setZip(inCreditCard.getZip());
  }

  public static final void copyDeviceInfo(DeviceInfo outDeviceInfo, DeviceInfo inDeviceInfo) {
    outDeviceInfo.setAccountNo(inDeviceInfo.getAccountNo());
    outDeviceInfo.setCustId(inDeviceInfo.getCustId());
    outDeviceInfo.setDeviceAssociation(inDeviceInfo.getDeviceAssociation());
    outDeviceInfo.setDeviceId(inDeviceInfo.getDeviceId());
    outDeviceInfo.setDeviceLabel(inDeviceInfo.getDeviceLabel());
    outDeviceInfo.setDeviceStatus(inDeviceInfo.getDeviceStatus());
    outDeviceInfo.setDeviceStatusId(inDeviceInfo.getDeviceStatusId());
    outDeviceInfo.setDeviceValue(inDeviceInfo.getDeviceValue());
    outDeviceInfo.setEffectiveDate(inDeviceInfo.getEffectiveDate());
    outDeviceInfo.setExpirationDate(inDeviceInfo.getExpirationDate());
    outDeviceInfo.setModDate(inDeviceInfo.getModDate());
  }

  public static final DeviceInfo clone(DeviceInfo inDeviceInfo) {
    DeviceInfo outDeviceInfo = new DeviceInfo();
    copyDeviceInfo(outDeviceInfo, inDeviceInfo);
    return outDeviceInfo;
  }

  public static final void copyNetworkInfo(NetworkInfo outNetworkInfo, NetworkInfo inNetworkInfo) {
    outNetworkInfo.setEffectivedate(inNetworkInfo.getEffectivedate());
    outNetworkInfo.setEffectivetime(inNetworkInfo.getEffectivetime());
    outNetworkInfo.setEsnmeiddec(inNetworkInfo.getEsnmeiddec());
    outNetworkInfo.setEsnmeidhex(inNetworkInfo.getEsnmeidhex());
    outNetworkInfo.setExpirationdate(inNetworkInfo.getExpirationdate());
    outNetworkInfo.setExpirationtime(inNetworkInfo.getExpirationtime());
    outNetworkInfo.setMdn(inNetworkInfo.getMdn());
    outNetworkInfo.setMsid(inNetworkInfo.getMsid());
    outNetworkInfo.setStatus(inNetworkInfo.getStatus());
  }

  public static final void copyAccount(Account outAccount, Account inAccount) {
    outAccount.setAccountCategory(inAccount.getAccountCategory());
    outAccount.setAccountno(inAccount.getAccountno());
    outAccount.setActiveDate(inAccount.getActiveDate());
    outAccount.setBalance(inAccount.getBalance());
    outAccount.setContactAddress1(inAccount.getContactAddress1());
    outAccount.setContactAddress2(inAccount.getContactAddress2());
    outAccount.setContactCity(inAccount.getContactCity());
    outAccount.setContactEmail(inAccount.getContactEmail());
    outAccount.setContactNumber(inAccount.getContactNumber());
    outAccount.setContactState(inAccount.getContactState());
    outAccount.setContactZip(inAccount.getContactZip());
    outAccount.setFirstname(inAccount.getFirstname());
    outAccount.setLastname(inAccount.getLastname());
    outAccount.setInactiveDate(inAccount.getInactiveDate());
    outAccount.setMiddlename(inAccount.getMiddlename());
  }

  /* ***********************************************************************
   * Conversion Methods
   * ***********************************************************************
   */

  public static final Customer toCustomer(User user) {
    Customer customer = new Customer();
    customer.setId(user.getUserId());
    return customer;
  }

  public static final CustAddress toCustAddress(User user, Address address) {
    CustAddress custAddress = new CustAddress();
    custAddress.setCustId(user.getUserId());
    custAddress.setIsDefault(address.isDefault() ? "Y" : "N");
    custAddress.setAddressId(address.getAddressId());
    custAddress.setAddress1(address.getAddress1());
    custAddress.setAddress2(address.getAddress2());
    custAddress.setAddress3(address.getAddress3());
    custAddress.setCity(address.getCity());
    custAddress.setState(address.getState());
    custAddress.setZip(address.getZip());
    custAddress.setAddressLabel(address.getLabel());
    return custAddress;
  }

  public static final Address getAddress(CreditCard creditCard) {
    Address address = new Address();
    address.setAddress1(creditCard.getAddress1());
    address.setAddress2(creditCard.getAddress2());
    address.setCity(creditCard.getCity());
    address.setState(creditCard.getState());
    address.setZip(creditCard.getZip());
    return address;
  }

  public static final Address toAddress(CustAddress custAddress) {
    Address address = new Address();
    address.setAddressId(custAddress.getAddressId());
    address.setLabel(custAddress.getAddressLabel());
    address.setAddress1(custAddress.getAddress1());
    address.setAddress2(custAddress.getAddress2());
    address.setAddress3(custAddress.getAddress3());
    address.setCity(custAddress.getCity());
    address.setState(custAddress.getState());
    address.setZip(custAddress.getZip());
    if (custAddress.getIsDefault() != null) {
      address.setDefault(custAddress.getIsDefault().equals("N") ? false : true);
    } else {
      address.setDefault(false);
    }
    return address;
  }

  public static final List<Address> toAddressList(List<CustAddress> custAddressList) {
    List<Address> addressList = new ArrayList<Address>();
    for (CustAddress custAddress : custAddressList) {
      addressList.add(toAddress(custAddress));
    }
    return addressList;
  }

  public static final ServiceInstance toServiceInstance(NetworkInfo networkInfo) {
    ServiceInstance serviceInstance = new ServiceInstance();
    serviceInstance.setExternalid(networkInfo.getMdn());
    return serviceInstance;
  }
}
