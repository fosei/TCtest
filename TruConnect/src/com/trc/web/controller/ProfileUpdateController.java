package com.trc.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.EmailException;
import com.trc.exception.management.AccountManagementException;
import com.trc.manager.AccountManager;
import com.trc.manager.UserManager;
import com.trc.security.encryption.Md5Encoder;
import com.trc.service.email.VelocityEmailService;
import com.trc.user.User;
import com.trc.user.security.UpdateEmail;
import com.trc.user.security.UpdatePassword;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.UserUpdateValidator;
import com.tscp.mvne.Account;

@Controller
@RequestMapping("/profile/update")
public class ProfileUpdateController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private AccountManager accountManager;
  @Autowired
  private UserUpdateValidator userUpdateValidator;
  @Autowired
  private VelocityEmailService velocityEmailService;

  public static final String UPDATE_KEY = "profileUpdate";
  public static final String UPDATE_ATTR = "profileUpdateAttr";
  public static final String UPDATE_STATUS = "profileUpdateStatus";
  public static final String UPDATE_EMAIL_NTF = "updateEmailNotification";
  public static final String UPDATE_EMAIL_VAL = "newEmail";
  public static final String ATTR_EMAIL = "email";
  public static final String ATTR_PASSWORD = "password";

  @RequestMapping(value = "/email", method = RequestMethod.GET)
  public ModelAndView updateEmail() {
    ResultModel model = new ResultModel("profile/update/email");
    model.addObject("updateEmail", new UpdateEmail());
    return model.getSuccess();
  }

  @RequestMapping(value = "/email", method = RequestMethod.POST)
  public ModelAndView requestUpdateEmail(HttpSession session, @ModelAttribute UpdateEmail updateEmail,
      BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/update/email");
    User user = userManager.getCurrentUser();
    userUpdateValidator.validateEmailChange(updateEmail, result, user);
    if (result.hasErrors()) {
      return model.getError();
    } else {
      try {
        SimpleMailMessage myMessage = new SimpleMailMessage();
        myMessage.setTo(updateEmail.getEmail());
        myMessage.setFrom("no-reply@truconnect.com");
        myMessage.setSubject("Verify Your New Email Address");
        Map<Object, Object> mailModel = new HashMap<Object, Object>();
        mailModel.put("user", user);
        mailModel.put("sessionId", session.getId());
        velocityEmailService.send("profileUpdate", myMessage, mailModel);
        session.setAttribute(UPDATE_EMAIL_VAL, updateEmail.getEmail());
        session.setAttribute(UPDATE_EMAIL_NTF, "sent");
      } catch (EmailException e) {
        e.printStackTrace();
      }
      return model.getSuccess();
    }
  }

  @RequestMapping(value = "/email/verify/{sessionId}", method = RequestMethod.GET)
  public String verifyUpdateEmail(HttpSession session, @PathVariable("sessionId") String sessionId) {
    if (session.getId().equals(sessionId)) {
      User user = userManager.getCurrentUser();
      String newEmail = (String) session.getAttribute(UPDATE_EMAIL_VAL);
      String oldEmail = user.getEmail();
      try {
        user.setUsername(newEmail);
        user.setEmail(newEmail);
        List<Account> accountList = accountManager.getAccounts(user);
        for (Account account : accountList) {
          account.setContactEmail(newEmail);
          accountManager.updateEmail(account);
        }
        userManager.updateUser(user);
        showProfileUpdateNotification(session, ATTR_EMAIL);
      } catch (AccountManagementException e) {
        e.printStackTrace();
        showProfileUpdateFailureNotification(session, ATTR_EMAIL);
        user.setEmail(oldEmail);
        userManager.updateUser(user);
      }
    }
    return "redirect:/profile";
  }

  @RequestMapping(value = "/password", method = RequestMethod.GET)
  public ModelAndView updatePassword() {
    ResultModel model = new ResultModel("profile/update/password");
    model.addObject("updatePassword", new UpdatePassword());
    return model.getSuccess();
  }

  @RequestMapping(value = "/password", method = RequestMethod.POST)
  public ModelAndView postUpdatePassword(HttpSession session, @ModelAttribute UpdatePassword updatePassword,
      BindingResult result) {
    ResultModel model = new ResultModel("redirect:/profile", "profile/update/password");
    User user = userManager.getCurrentUser();
    userUpdateValidator.validatePasswordChange(updatePassword, result, user);
    if (result.hasErrors()) {
      return model.getError();
    } else {
      user.setPassword(Md5Encoder.encode(updatePassword.getPassword()));
      userManager.updateUser(user);
      showProfileUpdateNotification(session, ATTR_PASSWORD);
      return model.getSuccess();
    }
  }

  private void showProfileUpdateNotification(HttpSession session, String attribute) {
    setUpdate(session, true);
    setUpdateStatus(session, true);
    setUpdateAttribute(session, attribute);
  }

  private void showProfileUpdateFailureNotification(HttpSession session, String attribute) {
    setUpdate(session, true);
    setUpdateStatus(session, false);
    setUpdateAttribute(session, attribute);
  }

  private void setUpdateAttribute(HttpSession session, String attribute) {
    session.setAttribute(UPDATE_ATTR, attribute);
  }

  private void setUpdate(HttpSession session, boolean status) {
    session.setAttribute(UPDATE_KEY, status);
  }

  private void setUpdateStatus(HttpSession session, boolean status) {
    session.setAttribute(UPDATE_STATUS, status);
  }
}
