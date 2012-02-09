package com.trc.util.logger.aspect;

import org.apache.commons.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;

import com.trc.manager.UserManager;
import com.trc.user.User;
import com.trc.web.session.SessionManager;

public class TraceInterceptor extends CustomizableTraceInterceptor {
  private static final long serialVersionUID = -5167767330467631668L;
  private UserManager userManager;

  public UserManager getUserManager() {
    return userManager;
  }

  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  protected void writeToLog(Log logger, String message, Throwable ex) {
    org.slf4j.Logger slf4jLogger = LoggerFactory.getLogger("truconnect");
    StringBuilder stampedMessage = new StringBuilder();
    stampedMessage.append(SessionManager.getCurrentSessionId());

    User controllingUser = userManager.getSessionAdmin();
    controllingUser = controllingUser == null ? userManager.getSessionManager() : controllingUser;
    String controllingUserStamp = controllingUser == null ? "" : " [" + controllingUser.getUserId() + "]"
        + controllingUser.getUsername() + " ";

    User currentUser = userManager.getCurrentUser();
    String currentUserStamp = currentUser == null ? " Anonymous " : " [" + currentUser.getUserId() + "]"
        + currentUser.getUsername() + " - ";

    stampedMessage.append(controllingUserStamp);
    stampedMessage.append(currentUserStamp);
    stampedMessage.append(message);

    slf4jLogger.trace(stampedMessage.toString(), ex);
  }
}
