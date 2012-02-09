package com.trc.web.validation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import com.trc.user.activation.Registration;
import com.trc.web.flow.util.WebFlowUtil;

@Component
public class JcaptchaValidator {

  public static void validate(HttpServletRequest request, Errors errors) {
    if (!SimpleImageCaptchaServlet.validateResponse(request, request.getParameter("jcaptcha"))) {
      errors.rejectValue("jcaptcha", "jcaptcha.incorrect", "Image Verificaiton failed");
    }
  }

  public static void validate(HttpServletRequest request, Registration reg, MessageContext messages) {
    if (!SimpleImageCaptchaServlet.validateResponse(request, request.getParameter("jcaptcha"))) {
      WebFlowUtil.addError(messages, "jcaptcha", "jcaptcha.incorrect", "Image Verification failed");
      reg.setJcaptcha(null);
    }
  }
}