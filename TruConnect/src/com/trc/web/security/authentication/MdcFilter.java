package com.trc.web.security.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

public class MdcFilter implements Filter {
  // private UserManager userManager;

  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {

    HttpServletRequest httprequest = (HttpServletRequest) request;
    SecurityContext context = (SecurityContext) httprequest.getSession().getAttribute(
        HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

    if (context != null) {
      Authentication authentication = context.getAuthentication();
      MDC.put("principal", authentication.getName());
      MDC.put("sessionId", httprequest.getSession().getId());
    } else {
      MDC.put("principal", "");
      MDC.put("sessionId", httprequest.getSession().getId());
    }
    chain.doFilter(request, response);
    // MDC.put("sessionId", SessionManager.getCurrentSessionId());
    // MDC.put("username", userManager.getCurrentUser().getUsername());
    // User internalUser = userManager.getSessionAdmin() == null ?
    // userManager.getSessionManager() == null ? null
    // : userManager.getSessionManager() : userManager.getSessionAdmin();
    // MDC.put("internalUser", internalUser.getUsername());
    // System.out.println("MDC FIlter! - " + MDC.get("sessionId") + " " +
    // MDC.get("username") + " "
    // + MDC.get("internalUser"));
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // ServletContext servletContext = filterConfig.getServletContext();
    // WebApplicationContext webApplicationContext =
    // WebApplicationContextUtils.getWebApplicationContext(servletContext);
    // AutowireCapableBeanFactory autowireCapableBeanFactory =
    // webApplicationContext.getAutowireCapableBeanFactory();
    // this.userManager = (UserManager)
    // autowireCapableBeanFactory.configureBean(this, "userManager");
  }
}
