package com.trc.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.trc.exception.EmailException;
import com.trc.exception.GatewayException;
import com.trc.exception.management.UserManagementException;
import com.trc.service.TruConnectEmailService;
import com.trc.service.email.VelocityEmailService;
import com.trc.user.User;
import com.trc.user.activation.Registration;
import com.trc.user.contact.ContactInfo;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.session.SessionManager;
import com.tscp.mvne.Account;

@Component
public class RegistrationManager implements RegistrationManagerModel {
  @Autowired
  private UserManager userManager;
  @Autowired
  private VelocityEmailService velocityEmailService;
  @Autowired
  private TruConnectEmailService truConnectEmailService;

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void reserveUserId(Registration registration) {
    int random = (new Random()).nextInt(99);
    String sessionTag = SessionManager.getCurrentSessionId().substring(0, 5);
    String registrationTag = "reserve_" + sessionTag + random + "_";

    // save the username/password/email/contactInfo
    ContactInfo contactInfo = registration.getUser().getContactInfo();
    String username = registration.getUser().getUsername();
    String password = registration.getUser().getPassword();
    String email = registration.getUser().getEmail();

    // get a user_id without reserving the username/password/email
    registration.getUser().setDateEnabled(new Date());
    registration.getUser().setUsername(registrationTag + username);
    // registration.getUser().setPassword(SessionManager.getCurrentSessionId());
    registration.getUser().setEmail(registrationTag + email);
    userManager.saveUser(registration.getUser());
    userManager.setSessionUser(registration.getUser());

    // restore the username/password/email
    registration.getUser().setUsername(username);
    // registration.getUser().setPassword(password);
    registration.getUser().setEmail(email);
    // registration.getUser().setContactInfo(contactInfo);
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void sendActivationEmail(User user, Account account) {
    try {
      truConnectEmailService.sendActivationEmail(user, account);
    } catch (GatewayException e) {
      e.printStackTrace();
    }
  }

  @Override
  @Deprecated
  @Loggable(value = LogLevel.TRACE)
  public void sendActivationEmail(User user) throws EmailException {
    try {
      if (user.getUserId() == 0) {
        user = userManager.getUserByUsername(user.getUsername());
      }
      SimpleMailMessage myMessage = new SimpleMailMessage();
      myMessage.setTo(user.getEmail());
      myMessage.setFrom("no-reply@truconnect.com");
      myMessage.setSubject("Your TruConnect Account");
      Map<Object, Object> mailModel = new HashMap<Object, Object>();
      mailModel.put("user", user);
      mailModel.put("code", user.getPassword().substring(0, 8));
      mailModel.put("password", user.getPassword());
      velocityEmailService.send("welcome", myMessage, mailModel);
    } catch (EmailException e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void cancelRegistration(Registration registration) throws UserManagementException {
    if (registration != null && registration.getUser().getUserId() != 0) {
      userManager.deleteUser(registration.getUser());
    }
  }
}