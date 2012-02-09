package com.trc.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.exception.management.AddressManagementException;
import com.trc.manager.AddressManager;
import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.user.contact.Address;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.AddressValidator;

@Controller
@RequestMapping("/profile")
public class ProfileController extends EncryptedController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AddressManager addressManager;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView showProfile(HttpSession session) {
    ResultModel model = new ResultModel("profile/profile");
    User user = userManager.getCurrentUser();
    if (notificationSent(session)) {
      showNotification(session, model);
    } else {
      hideNotification(model);
    }

    if (profileUpdated(session)) {
      showProfileUpdate(session, model);
    } else {
      hideProfileUpdate(model);
    }

    clearProfileUpdateCache(session);

    List<Address> addresses;
    try {
      addresses = addressManager.getAllAddresses(userManager.getCurrentUser());
      encodeAddressIds(addresses);
      model.addObject("addresses", addresses);
    } catch (AddressManagementException e) {
      e.printStackTrace();
    }

    model.addObject("user", user);
    return model.getSuccess();
  }

  @RequestMapping(value = "/address/add", method = RequestMethod.GET)
  public ModelAndView addAddress(HttpSession session) {
    ResultModel model = new ResultModel("profile/address/addAddress");
    model.addObject("states", Config.states.entrySet());
    model.addObject("months", Config.months.entrySet());
    model.addObject("years", Config.yearsFuture.entrySet());
    model.addObject("address", new Address());
    return model.getSuccess();
  }

  @RequestMapping(value = "/address/add", method = RequestMethod.POST)
  public ModelAndView postAddAddress(HttpSession session, @ModelAttribute Address address, BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/address/addAddress");
    User user = userManager.getCurrentUser();
    AddressValidator addressValidator = new AddressValidator();
    addressValidator.validate(address, result);
    if (result.hasErrors()) {
      model.addObject("states", Config.states.entrySet());
      model.addObject("months", Config.months.entrySet());
      model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getError();
    } else {
      try {
        addressManager.addAddress(user, address);
        return model.getSuccess();
      } catch (AddressManagementException e) {
        return model.getException();
      }
    }
  }

  @RequestMapping(value = "/address/edit/{encodedAddressId}", method = RequestMethod.GET)
  public ModelAndView editAddress(HttpSession session, @PathVariable("encodedAddressId") String encodedAddressId) {
    ResultModel model = new ResultModel("profile/address/editAddress");
    User user = userManager.getCurrentUser();
    try {
      Address address = addressManager.getAddress(user, super.decryptId(encodedAddressId));
      model.addObject("states", Config.states.entrySet());
      model.addObject("months", Config.months.entrySet());
      model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getAccessException();
    }
  }

  @RequestMapping(value = "/address/edit/{encodedAddressId}", method = RequestMethod.POST)
  public ModelAndView postEditAddress(HttpSession session, @ModelAttribute Address address, BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/address/editAddress");
    User user = userManager.getCurrentUser();
    AddressValidator addressValidator = new AddressValidator();
    addressValidator.validate(address, result);
    if (result.hasErrors()) {
      model.addObject("states", Config.states.entrySet());
      model.addObject("months", Config.months.entrySet());
      model.addObject("years", Config.yearsFuture.entrySet());
      model.addObject("address", address);
      return model.getError();
    } else {
      try {
        addressManager.updateAddress(user, address);
        return model.getSuccess();
      } catch (AddressManagementException e) {
        return model.getException();
      }
    }
  }

  @RequestMapping(value = "/address/remove/{encodedAddressId}", method = RequestMethod.GET)
  public ModelAndView removeAddress(HttpSession session, @PathVariable("encodedAddressId") String encodedAddressId) {
    ResultModel model = new ResultModel("profile/address/removeAddress");
    User user = userManager.getCurrentUser();
    try {
      Address address = addressManager.getAddress(user, super.decryptId(encodedAddressId));
      model.addObject("address", address);
      model.addObject("states", Config.states.entrySet());
      model.addObject("months", Config.months.entrySet());
      model.addObject("years", Config.yearsFuture.entrySet());
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getException();
    }
  }

  @RequestMapping(value = "/address/remove/{encodedAddressId}", method = RequestMethod.POST)
  public ModelAndView postRemoveAddress(HttpSession session, @ModelAttribute Address address) {
    ResultModel model = new ResultModel("redirect:/profile");
    User user = userManager.getCurrentUser();
    try {
      addressManager.removeAddress(user, address);
      return model.getSuccess();
    } catch (AddressManagementException e) {
      return model.getException();
    }
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
  @RequestMapping(value = "/user/enable", method = RequestMethod.GET)
  public String enableUser() {
    if (Config.admin) {
      User user = userManager.getCurrentUser();
      user.setEnabled(true);
      userManager.updateUser(user);
    }
    return "redirect:/profile";
  }

  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
  @RequestMapping(value = "/user/disable", method = RequestMethod.GET)
  public String disableUser() {
    if (Config.admin) {
      User user = userManager.getCurrentUser();
      user.setEnabled(false);
      user.setDateDisabled(new Date());
      userManager.updateUser(user);
    }
    return "redirect:/profile";
  }

  private void encodeAddressIds(List<Address> addressList) {
    for (Address address : addressList) {
      address.setEncodedAddressId(super.encryptId(address.getAddressId()));
    }
  }

  private boolean notificationSent(HttpSession session) {
    return session.getAttribute(ProfileUpdateController.UPDATE_EMAIL_NTF) == null ? false : true;
  }

  private void showNotification(HttpSession session, ResultModel model) {
    model.addObject(ProfileUpdateController.UPDATE_EMAIL_NTF, true);
    model.addObject(ProfileUpdateController.UPDATE_EMAIL_VAL, (String) session
        .getAttribute(ProfileUpdateController.UPDATE_EMAIL_VAL));
  }

  private void hideNotification(ResultModel model) {
    model.addObject(ProfileUpdateController.UPDATE_EMAIL_NTF, false);
  }

  private boolean profileUpdated(HttpSession session) {
    return session.getAttribute(ProfileUpdateController.UPDATE_KEY) == null ? false : true;
  }

  private void showProfileUpdate(HttpSession session, ResultModel model) {
    boolean success = (Boolean) session.getAttribute(ProfileUpdateController.UPDATE_STATUS);
    String updatedProperty = (String) session.getAttribute(ProfileUpdateController.UPDATE_ATTR);
    if (success) {
      model.addObject(ProfileUpdateController.UPDATE_KEY, true);
      model.addObject(ProfileUpdateController.UPDATE_STATUS, true);
      model.addObject(ProfileUpdateController.UPDATE_ATTR, updatedProperty);
    }
  }

  private void hideProfileUpdate(ResultModel model) {
    model.addObject(ProfileUpdateController.UPDATE_KEY, false);
  }

  private void clearProfileUpdateCache(HttpSession session) {
    session.removeAttribute(ProfileUpdateController.UPDATE_EMAIL_NTF);
    session.removeAttribute(ProfileUpdateController.UPDATE_KEY);
    session.removeAttribute(ProfileUpdateController.UPDATE_STATUS);
    session.removeAttribute(ProfileUpdateController.UPDATE_ATTR);
  }
}