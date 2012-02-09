package com.trc.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trc.exception.EmailException;
import com.trc.manager.SecurityQuestionManager;
import com.trc.manager.UserManager;
import com.trc.security.encryption.Md5Encoder;
import com.trc.service.email.VelocityEmailService;
import com.trc.user.User;
import com.trc.user.security.SecurityQuestion;
import com.trc.user.security.UpdatePassword;
import com.trc.user.security.VerifyIdentity;
import com.trc.web.model.ResultModel;
import com.trc.web.validation.EmailValidator;
import com.trc.web.validation.UserUpdateValidator;

@Controller
@RequestMapping("/retrieve")
public class RetrieveController {
  @Autowired
  private UserManager userManager;
  @Autowired
  private SecurityQuestionManager securityQuestionManager;
  @Autowired
  private UserUpdateValidator userUpdateValidator;
  @Autowired
  private VelocityEmailService velocityEmailService;

  private String getEmailKey(HttpSession session) {
    return "resetEmail" + session.getId();
  }

  private void storeEmail(HttpSession session, String email) {
    session.setAttribute(getEmailKey(session), email);
  }

  private String retrieveEmail(HttpSession session) {
    return (String) session.getAttribute(getEmailKey(session));
  }

  @RequestMapping(value = "/password", method = RequestMethod.GET)
  public ModelAndView resetPassword() {
    ResultModel model = new ResultModel("retrievePassword/requestEmail");
    model.addObject("verifyIdentity", new VerifyIdentity());
    return model.getSuccess();
  }

  @RequestMapping(value = "/password", method = RequestMethod.POST)
  public ModelAndView sendResetEmail(HttpSession session, @ModelAttribute VerifyIdentity verifyIdentity, Errors errors) {
    ResultModel model = new ResultModel("retrievePassword/emailSentStatus", "retrievePassword/requestEmail");
    if (!EmailValidator.checkEmail(verifyIdentity.getEmail())) {
      errors.rejectValue("email", "email.invalid", "Not a valid email address.");
      return model.getError();
    }
    storeEmail(session, verifyIdentity.getEmail());
    User user = userManager.getUserByEmail(verifyIdentity.getEmail());
    if (user == null) {
      // errors.reject("email.notExist", "User &quot;" +
      // verifyIdentity.getEmail() + "&quot; not found");
      // return model.getError();
      return model.getSuccess();
    } else {
      Map<Object, Object> mailModel = new HashMap<Object, Object>();
      mailModel.put("key", session.getId());
      mailModel.put("user", user);
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(verifyIdentity.getEmail());
      message.setFrom("no-reply@truconnect.com");
      message.setSubject("A request to reset your password has been made.");
      try {
        velocityEmailService.send("passwordReset", message, mailModel);
      } catch (EmailException e) {
        e.printStackTrace();
      }
    }
    return model.getSuccess();
  }

  private boolean isValidKey(HttpSession session, String key) {
    return key.equals(session.getId());
  }

  private boolean isCorrectAnswer(User user, VerifyIdentity verifyIdentity) {
    return user.getUserHint().getHintAnswer().equalsIgnoreCase(verifyIdentity.getHintAnswer().trim());
  }

  @RequestMapping(value = "/password/verify/{key}", method = RequestMethod.GET)
  public ModelAndView requestSecurityQuestion(HttpSession session, @PathVariable("key") String key) {
    ResultModel model = new ResultModel("retrievePassword/requestSecurityQuestion");
    if (isValidKey(session, key)) {
      String email = retrieveEmail(session);
      User user = userManager.getUserByEmail(email);
      if (user != null) {
        SecurityQuestion hint = securityQuestionManager.getSecurityQuestion(user.getUserHint().getHintId());
        model.addObject("verifyIdentity", new VerifyIdentity());
        model.addObject("question", hint.getHintQuestion());
      }
    } else {
      return model.getTimeout();
    }
    return model.getSuccess();
  }

  @RequestMapping(value = "/password/verify/{key}", method = RequestMethod.POST)
  public ModelAndView verifySecurityQuestion(HttpSession session, @PathVariable("key") String key,
      @ModelAttribute VerifyIdentity verifyIdentity, Errors errors) {
    ResultModel model = new ResultModel("redirect:/retrieve/password/update/" + key,
        "retrievePassword/requestSecurityQuestion");
    if (isValidKey(session, key)) {
      String email = (String) session.getAttribute(getEmailKey(session));
      User user = userManager.getUserByEmail(email);
      if (!isCorrectAnswer(user, verifyIdentity)) {
        errors.reject("securtyQuestion.answer.incorrect", "Your response did not match your saved answer");
        SecurityQuestion hint = securityQuestionManager.getSecurityQuestion(user.getUserHint().getHintId());
        model.addObject("verifyIdentity", new VerifyIdentity());
        model.addObject("question", hint.getHintQuestion());
        return model.getError();
      } else {
        return model.getSuccess();
      }
    } else {
      return model.getTimeout();
    }
  }

  @RequestMapping(value = "/password/update/{key}", method = RequestMethod.GET)
  public ModelAndView newPassword(HttpSession session, @PathVariable("key") String key) {
    ResultModel model = new ResultModel("retrievePassword/updatePassword");
    if (isValidKey(session, key)) {
      String email = retrieveEmail(session);
      User user = userManager.getUserByEmail(email);
      model.addObject("user", user);
      model.addObject("updatePassword", new UpdatePassword());
      return model.getSuccess();
    } else {
      return model.getTimeout();
    }
  }

  @RequestMapping(value = "/password/update/{key}", method = RequestMethod.POST)
  public ModelAndView postNewPassword(HttpSession session, @PathVariable("key") String key,
      @ModelAttribute("updatePassword") UpdatePassword updatePassword, BindingResult result) {
    ResultModel model = new ResultModel("retrievePassword/passwordResetSuccess", "retrievePassword/updatePassword");
    if (key.equals(session.getId())) {
      String email = retrieveEmail(session);
      User user = userManager.getUserByEmail(email);
      userUpdateValidator.validateNewPassword(updatePassword, result);
      if (result.hasErrors()) {
        model.addObject("user", user);
        model.addObject("updatePassword", new UpdatePassword());
        return model.getError();
      } else {
        user.setPassword(Md5Encoder.encode(updatePassword.getPassword()));
        userManager.updateUser(user);
        return model.getSuccess();
      }
    } else {
      return model.getTimeout();
    }
  }

}