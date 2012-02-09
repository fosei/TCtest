package com.trc.web.model;

import org.springframework.web.servlet.ModelAndView;

public class ResultModel {
  private ModelAndView model = new ModelAndView();
  private String successViewName;
  private String errorViewName;
  private String accessDeniedViewName = "exception/accessDenied";
  private String accessExceptionViewName = "exception/dataAccessException";
  private String exceptionViewName = "exception/uncaughtException";
  private String timeoutViewName = "exception/timeout";

  public ResultModel(String successView, String errorView) {
    setSuccessViewName(successView);
    setErrorViewName(errorView);
    setViewName(errorView);
  }

  public ResultModel(String view) {
    setSuccessViewName(view);
    setErrorViewName(view);
    setViewName(view);
  }

  public ModelAndView getModel() {
    return model;
  }

  public void setModel(ModelAndView model) {
    this.model = model;
  }

  public void setViewName(String viewName) {
    getModel().setViewName(viewName);
  }

  public ModelAndView addObject(String attributeName, Object attributeValue) {
    return getModel().addObject(attributeName, attributeValue);
  }

  public String getSuccessViewName() {
    return successViewName;
  }

  public void setSuccessViewName(String successViewName) {
    this.successViewName = successViewName;
  }

  public String getErrorViewName() {
    return errorViewName;
  }

  public void setErrorViewName(String errorViewName) {
    this.errorViewName = errorViewName;
  }

  public String getAccessExceptionViewName() {
    return accessExceptionViewName;
  }

  public void setAccessExceptionViewName(String accessExceptionViewName) {
    this.accessExceptionViewName = accessExceptionViewName;
  }

  public String getAccessDeniedViewName() {
    return accessDeniedViewName;
  }

  public void setAccessDeniedViewName(String accessDeniedViewName) {
    this.accessDeniedViewName = accessDeniedViewName;
  }

  public String getExceptionViewName() {
    return exceptionViewName;
  }

  public void setExceptionViewName(String exceptionViewName) {
    this.exceptionViewName = exceptionViewName;
  }

  public String getTimeoutViewName() {
    return timeoutViewName;
  }

  public void setTimeoutViewName(String timeoutViewName) {
    this.timeoutViewName = timeoutViewName;
  }

  public ModelAndView getSuccess() {
    setViewName(getSuccessViewName());
    return model;
  }

  public ModelAndView getError() {
    setViewName(getErrorViewName());
    return model;
  }

  public ModelAndView getException() {
    setViewName(getExceptionViewName());
    return model;
  }

  public ModelAndView getAccessException() {
    setViewName(getAccessExceptionViewName());
    return model;
  }

  public ModelAndView getTimeout() {
    setViewName(getTimeoutViewName());
    return model;
  }

  public ModelAndView getAccessDenied() {
    setViewName(getAccessDeniedViewName());
    return model;
  }

}
