package com.trc.util.logger.aspect;

import java.text.MessageFormat;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.user.User;
import com.trc.user.account.AccountDetail;
import com.trc.user.activation.Registration;
import com.trc.user.contact.Address;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.Slf4jLogger;
import com.tscp.mvne.Account;
import com.tscp.mvne.CreditCard;
import com.tscp.mvne.Customer;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Aspect
@Component
public class LoggingAspect {
  private static final String BEFORE_STRING = "{0} <{1}>";
  private static final String BEFORE_WITH_PARAMS_STRING = "{0} <{1}> params {2}";
  private static final String AFTER_THROWING = "{0} <{1}> EXCEPTION \"{2}\" with params ({3})";
  private static final String AFTER_THROWING_NO_PARAMS = "{0} <{1}> EXCEPTION \"{2}\"";
  private static final String AFTER_RETURNING = "{0} <{1}> returned {2} in {3}ms";
  private static final String AFTER_RETURNING_VOID = "{0} <{1}> returned in {2}ms";
  private static final String ARG_PATTERN = "<{0}({1})>";
  private static final String ARG_PATTERN_SINGLE = "<{0}>";
  private static final String ARG_PATTERN_DOUBLE = "<{0}:{1}>";

  private long start;
  private long elapsedTime;
  private StartTimeStack startTimeStack = new StartTimeStack();

  @Resource
  private Slf4jLogger logger;
  @Autowired
  private LoggingHelper loggingHelper;

  /**
   * This class is used to store the start times of nested method calls. These
   * values are used to calculate the total time it took to process the method.
   * 
   * @author Tachikoma
   * 
   */
  private class StartTimeStack extends Stack<Long> {
    private static final long serialVersionUID = 1L;

    public boolean add(Long time) {
      return super.add(time);
    }

    public Long pop() {
      Long lastTime = super.pop();
      if (lastTime == null) {
        return new Long(0);
      } else {
        return lastTime;
      }
    }
  }

  @Before(value = "@annotation(trace)", argNames = "joinPoint, trace")
  public void before(JoinPoint joinPoint, Loggable loggable) {
    start = System.currentTimeMillis();
    startTimeStack.add(start);

    Class<? extends Object> clazz = joinPoint.getTarget().getClass();
    String methodName = joinPoint.getSignature().getName();

    if (ArrayUtils.isEmpty(joinPoint.getArgs())) {
      logger.log(loggable.value(), clazz, null, BEFORE_STRING, loggingHelper.getUserStamp(), methodName,
          constructArgString(clazz, joinPoint.getArgs()));
    } else {
      logger.log(loggable.value(), clazz, null, BEFORE_WITH_PARAMS_STRING, loggingHelper.getUserStamp(), methodName,
          constructArgString(clazz, joinPoint.getArgs()));
    }
  }

  @AfterThrowing(value = "@annotation(com.trc.util.logger.aspect.Loggable)", throwing = "throwable", argNames = "joinPoint, throwable")
  public void afterThrowing(JoinPoint joinPoint, Throwable throwable) {
    Class<? extends Object> clazz = joinPoint.getTarget().getClass();
    String name = joinPoint.getSignature().getName();
    // logger.log(LogLevel.ERROR, clazz, throwable, AFTER_THROWING,
    // loggingHelper.getUserStamp(), name, throwable
    // .getMessage(), constructArgString(clazz, joinPoint.getArgs()));
    logger.log(LogLevel.ERROR, clazz, throwable, AFTER_THROWING_NO_PARAMS, loggingHelper.getUserStamp(), name,
        throwable.getMessage());
  }

  @AfterReturning(value = "@annotation(trace)", returning = "returnValue", argNames = "joinPoint, trace, returnValue")
  public void afterReturning(JoinPoint joinPoint, Loggable loggable, Object returnValue) {
    elapsedTime = System.currentTimeMillis() - startTimeStack.pop();
    Class<? extends Object> clazz = joinPoint.getTarget().getClass();
    String name = joinPoint.getSignature().getName();

    if (joinPoint.getSignature() instanceof MethodSignature) {
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      Class<?> returnType = signature.getReturnType();
      if (returnType.getName().compareTo("void") == 0) {
        logger
            .log(loggable.value(), clazz, null, AFTER_RETURNING_VOID, loggingHelper.getUserStamp(), name, elapsedTime);
        start = 0;
        elapsedTime = 0;
        return;
      }
    }
    logger.log(loggable.value(), clazz, null, AFTER_RETURNING, loggingHelper.getUserStamp(), name, constructArgString(
        clazz, returnValue), elapsedTime);
    start = 0;
    elapsedTime = 0;
  }

  private String formatArg(Object... args) {
    if (args.length == 1) {
      return format(ARG_PATTERN_SINGLE, args);
    } else if (args.length == 2) {
      return format(ARG_PATTERN_DOUBLE, args);
    } else {
      return format(ARG_PATTERN, args);
    }
  }

  private String format(String pattern, Object... args) {
    return MessageFormat.format(pattern, args);
  }

  private String constructArgString(Class<?> clazz, Object... args) {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < args.length; i++) {
      buffer.append(constructArg(args[i]));
      if (i != args.length - 1) {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

  private String constructArg(List<Object> args) {
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < args.size(); i++) {
      buffer.append(constructArg(args.get(i)));
      if (i != args.size() - 1) {
        buffer.append(", ");
      }
    }
    return buffer.toString();
  }

  @SuppressWarnings("unchecked")
  @Deprecated
  private String constructArg(Object arg) {
    StringBuffer buffer = new StringBuffer();
    Object clazz = null;
    String id = null;
    Object descriptor = null;

    if (arg == null) {
      clazz = null;
      id = null;
      descriptor = null;
    } else if (arg instanceof java.lang.String) {
      String string = (String) arg;
      id = "String";
      descriptor = string;
      clazz = string.getClass().getSimpleName();
    } else if (arg instanceof com.trc.user.User) {
      User user = (User) arg;
      clazz = user.getClass().getSimpleName();
      id = Integer.toString(user.getUserId());
      descriptor = user.getUsername();
    } else if (arg instanceof com.tscp.mvne.Customer) {
      Customer customer = (Customer) arg;
      clazz = customer.getClass().getSimpleName();
      id = Integer.toString(customer.getId());
      descriptor = null;
    } else if (arg instanceof com.tscp.mvne.DeviceInfo) {
      DeviceInfo deviceInfo = (DeviceInfo) arg;
      clazz = deviceInfo.getClass().getSimpleName();
      id = Integer.toString(deviceInfo.getDeviceId());
      descriptor = deviceInfo.getDeviceLabel();
    } else if (arg instanceof com.tscp.mvne.CreditCard) {
      CreditCard creditCard = (CreditCard) arg;
      clazz = creditCard.getClass().getSimpleName();
      id = Integer.toString(creditCard.getPaymentid());
      descriptor = creditCard.getCreditCardNumber();
    } else if (arg instanceof com.tscp.mvne.Account) {
      Account account = (Account) arg;
      clazz = account.getClass().getSimpleName();
      id = Integer.toString(account.getAccountno());
      descriptor = account.getFirstname() + " " + account.getLastname();
    } else if (arg instanceof com.trc.user.activation.Registration) {
      Registration registration = (Registration) arg;
      clazz = registration.getClass().getSimpleName();
      id = Integer.toString(registration.getUser().getUserId());
      descriptor = registration.getUser().getUsername();
    } else if (arg instanceof com.trc.user.account.AccountDetail) {
      AccountDetail accountDetail = (AccountDetail) arg;
      clazz = accountDetail.getClass().getSimpleName();
      id = Integer.toString(accountDetail.getAccount().getAccountno());
      descriptor = accountDetail.getDeviceInfo().getDeviceLabel();
    } else if (arg instanceof com.trc.user.contact.Address) {
      Address address = (Address) arg;
      clazz = address.getClass().getSimpleName();
      id = Integer.toString(address.getAddressId());
      descriptor = address.getAddress1();
    } else if (arg instanceof com.tscp.mvne.NetworkInfo) {
      NetworkInfo networkInfo = (NetworkInfo) arg;
      clazz = networkInfo.getClass().getSimpleName();
      id = networkInfo.getEsnmeiddec();
      descriptor = networkInfo.getMdn();
    } else if (arg instanceof com.tscp.mvne.ServiceInstance) {
      ServiceInstance serviceInstance = (ServiceInstance) arg;
      clazz = serviceInstance.getClass().getSimpleName();
      id = serviceInstance.getExternalid();
      descriptor = serviceInstance.getSubscrno();
    } else {
      clazz = arg.getClass().getSimpleName();
      id = "unknown";
      descriptor = arg;
    }

    if (arg instanceof java.util.Collection) {
      buffer.append(constructArg((List<Object>) arg));
    } else if (id != null && id.equals("unknown")) {
      buffer.append(formatArg(clazz, arg));
    } else {
      buffer.append(formatArg(id, descriptor));
    }
    return buffer.toString();
  }
}