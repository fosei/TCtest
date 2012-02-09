package com.trc.web.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext appContext) throws BeansException {
    applicationContext = appContext;
  }

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
