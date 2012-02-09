package com.trc.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.trc.security.encryption.StringEncrypter;

public final class SessionManager {

  public static final String getKey(SessionKey sessionKey) {
    String result;
    switch (sessionKey) {
    case DEVICE_SWAP:
      result = getCurrentSessionId() + "_DEVICE_SWAP";
      break;
    case DEVICE_RENAME:
      result = getCurrentSessionId() + "_DEVICE_RENAME";
      break;
    case DEVICE_DEACTIVATE:
      result = getCurrentSessionId() + "_DEVICE_DEACTIVATE";
      break;
    case DEVICE_REACTIVATE:
      result = getCurrentSessionId() + "DEVICE_REACTIVATE";
      break;
    case DEVICE_ACCOUNT:
      result = getCurrentSessionId() + "_DEVICE_ACCOUNT";
      break;
    case DEVICE_ACCESSFEEDATE:
      result = getCurrentSessionId() + "_DEVICE_ACCESSFEEDATE";
      break;
    case DEVICE_ACCOUNTDETAIL:
      result = getCurrentSessionId() + "_DEVICE_TOPUP";
      break;
    case CREDITCARD_UPDATE:
      result = getCurrentSessionId() + "_CREDITCARD_UPDATE";
      break;
    case ENCRYPTER:
      result = "ENCRYPTER";
      break;
    default:
      result = null;
    }
    return result;
  }

  public static final HttpSession getCurrentSession() {
    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(true);
  }

  public static final String getCurrentSessionId() {
    return getCurrentSession().getId();
  }

  public static final Object get(SessionKey sessionKey) {
    return get(getKey(sessionKey));
  }

  public static final Object get(String key) {
    return get(getCurrentSession(), key);
  }

  public static final void set(SessionKey sessionKey, Object obj) {
    set(getKey(sessionKey), obj);
  }

  public static final void set(String key, Object obj) {
    set(getCurrentSession(), key, obj);
  }

  public static final void clear(String key) {
    clear(getCurrentSession(), key);
  }

  private static final void set(HttpSession session, String key, Object obj) {
    if (session != null) {
      session.setAttribute(key, obj);
    }
  }

  private static final Object get(HttpSession session, String key) {
    if (session != null) {
      Object obj = session.getAttribute(key);
      return obj;
    } else {
      return null;
    }
  }

  private static final void clear(HttpSession session, String key) {
    if (session != null) {
      session.removeAttribute(key);
    }
  }

  public static final StringEncrypter getEncrypter() {
    StringEncrypter stringEncrypter = (StringEncrypter) SessionManager.get(SessionKey.ENCRYPTER);
    if (stringEncrypter == null) {
      stringEncrypter = new StringEncrypter(SessionManager.getCurrentSession().getId());
      SessionManager.set(SessionKey.ENCRYPTER, stringEncrypter);
    }
    return stringEncrypter;
  }
}
