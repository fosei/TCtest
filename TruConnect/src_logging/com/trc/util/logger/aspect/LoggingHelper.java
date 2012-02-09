package com.trc.util.logger.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.session.SessionManager;

@Component
public class LoggingHelper {
  @Autowired
  private UserManager userManager;

  private User getControllingUser() {
    return getAdmin() == null ? getManager() : getAdmin();
  }

  private User getAdmin() {
    User admin = userManager.getSessionAdmin();
    if (admin == null) {
      admin = userManager.getLoggedInUser();
      if (admin.isAdmin()) {
        return admin;
      } else {
        return null;
      }
    } else {
      return admin;
    }
  }

  private User getManager() {
    User manager = userManager.getSessionManager();
    if (manager == null) {
      manager = userManager.getLoggedInUser();
      if (manager.isAdmin()) {
        return manager;
      } else {
        return null;
      }
    } else {
      return manager;
    }
  }

  private User getCurrentUser() {
    return userManager.getSessionUser() == null ? userManager.getLoggedInUser() : userManager.getSessionUser();
  }

  private String getUserStamp(User user) {
    if (user != null) {
      StringBuilder userStamp = new StringBuilder();
      if (user.isAdmin()) {
        userStamp.append("Admin:");
      } else if (user.isManager()) {
        userStamp.append("Manager:");
      } else if (!user.isEnabled()) {
        userStamp.append("Reserve:");
      }
      userStamp.append(user.getUserId());
      userStamp.append("(").append(user.getUsername()).append(") -");
      return userStamp.toString();
    } else {
      return "";
    }
  }

  public String getUserStamp() {
    StringBuilder userStamp = new StringBuilder();
    User currentUser = getCurrentUser();
    User controllingUser = getControllingUser();
    if (controllingUser != null) {
      userStamp.append(getUserStamp(controllingUser));
    }
    userStamp.append("[").append(SessionManager.getCurrentSessionId()).append("] ");
    userStamp.append(getUserStamp(currentUser));
    return userStamp.toString();
  }
}
