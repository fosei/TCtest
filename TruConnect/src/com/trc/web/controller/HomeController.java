package com.trc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trc.manager.UserManager;

@Controller
@RequestMapping("/")
public class HomeController {
  private static final String contextPath = "TruConnect";
  @Autowired
  private UserManager userManager;

  // private static ApplicationContext applicationContext;
  // @PostConstruct
  // public void setContextPath() {
  // contextPath = applicationContext.getDisplayName();
  // contextPath = contextPath.substring(contextPath.indexOf("'") + 1);
  // contextPath = contextPath.indexOf("-") == -1 ? contextPath :
  // contextPath.substring(0, contextPath.indexOf("-"));
  // }

  @RequestMapping(method = RequestMethod.GET)
  public String home() {
    for (GrantedAuthority grantedAuthority : userManager.getCurrentUser().getAuthorities()) {
      if (grantedAuthority.getAuthority().equals("ROLE_ANONYMOUS")) {
        return "redirect:/home";
      } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
        return "redirect:/admin";
      } else if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
        return "redirect:/admin";
      }
    }
    return "redirect:/manage";
  }

  private String injectContextPath(HttpServletRequest request, String page, boolean redirect) {
    if (request.getContextPath().trim().isEmpty()) {
      return "redirect:/" + contextPath + "/" + page;
    } else {
      return redirect ? "redirect:/" + page : page;
    }
  }

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String showHome(HttpServletRequest request) {
    String path = injectContextPath(request, "home", false);
    return path;
  }

  @RequestMapping(value = "/activation", method = RequestMethod.GET)
  public String activate(HttpServletRequest request) {
    String path = injectContextPath(request, "registration", true);
    return path;
  }

  @RequestMapping(value = "/manage", method = RequestMethod.GET)
  public String manage(HttpServletRequest request) {
    String path = injectContextPath(request, "account", true);
    return path;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String login(HttpServletRequest request) {
    if (userManager.getCurrentUser().isAuthenticated()) {
      return "redirect:/manage";
    } else {
      String path = injectContextPath(request, "login", false);
      return path;
    }
  }

  @RequestMapping(value = "/timeout", method = RequestMethod.GET)
  public String timeout() {
    return "exception/timeout";
  }

}