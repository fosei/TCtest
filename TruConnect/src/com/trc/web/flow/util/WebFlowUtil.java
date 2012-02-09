package com.trc.web.flow.util;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.RequestContextHolder;

public final class WebFlowUtil {

  public static void addError(MessageContext messages, String source, String code, String defaultText) {
    messages.addMessage(new MessageBuilder().error().source(source).code(code).defaultText(defaultText).build());
  }

  public static void addError(MessageContext messages, String source, String code) {
    messages.addMessage(new MessageBuilder().error().source(source).code(code).build());
  }

  public static void addContextError(RequestContext context, String message) {
    MessageResolver errorMessage = new MessageBuilder().error().defaultText(message).build();
    context.getMessageContext().addMessage(errorMessage);
  }

  public static void addContextError(RequestContext context, String code, String defaultText) {
    MessageResolver errorMessage = new MessageBuilder().error().code(code).defaultText(defaultText).build();
    context.getMessageContext().addMessage(errorMessage);
  }

  public static RequestContext getRequestContext() {
    return RequestContextHolder.getRequestContext();
  }

  public static void addError(String message) {
    addContextError(getRequestContext(), message);
  }

  public static void addError(String code, String message) {
    addContextError(getRequestContext(), code, message);
  }
}
