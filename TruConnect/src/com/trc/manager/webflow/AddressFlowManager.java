package com.trc.manager.webflow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.WebFlowException;
import com.trc.exception.management.AddressManagementException;
import com.trc.manager.AddressManager;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.trc.web.flow.util.WebFlowUtil;

@Component
public class AddressFlowManager {
  @Autowired
  private AddressManager addressManager;

  private static final String ERROR_GET_ADDRESS = "An error occured while retrieving your information. Please try again.";
  private static final String ERROR_ADD_ADDRESS = "An error occurred while saving your information. Please try again.";

  public List<Address> getAddresses(User user) throws WebFlowException {
    try {
      return addressManager.getAllAddresses(user);
    } catch (AddressManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_GET_ADDRESS);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void addAddress(User user, Address address) throws WebFlowException {
    if (address.getAddressId() == 0) {
      if (!user.isEnabled()) {
        address.setDefault(true);
        address.setLabel("Default Address (" + address.getAddress1() + ")");
      }
      try {
        addressManager.addAddress(user, address);
      } catch (AddressManagementException e) {
        e.printStackTrace();
        WebFlowUtil.addError(ERROR_ADD_ADDRESS);
        throw new WebFlowException(e.getMessage(), e.getCause());
      }
    }
  }

  public boolean isNewAddress(Address address) {
    return address.getAddressId() == 0;
  }
}
