package com.trc.web.context;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHolderFacade implements SecurityContextFacade {

  @Override
  public SecurityContext getContext() {
    return SecurityContextHolder.getContext();
  }

  @Override
  public void setContext(SecurityContext securityContext) {
    SecurityContextHolder.setContext(securityContext);
  }
}
