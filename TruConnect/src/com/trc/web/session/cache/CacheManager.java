package com.trc.web.session.cache;

import org.springframework.stereotype.Component;

import com.trc.web.session.SessionManager;

@Component
public final class CacheManager {

  public static final String getKey(CacheKey cacheKey) {
    String result;
    switch (cacheKey) {
    case DEVICES:
      result = "DEVICE_LIST";
      break;
    case CREDIT_CARDS:
      result = "CREDIT_CARD_LIST";
      break;
    case ADDRESSES:
      result = "ADDRESS_LIST";
      break;
    case ACCOUNTS:
      result = "ACCOUNT_LIST";
      break;
    case ACCOUNT_DETAIL:
      result = "ACCOUNT_DETAIL_LIST";
      break;
    default:
      result = null;
    }
    return result;
  }

  public static final void set(CacheKey cacheKey, Object obj) {
    SessionManager.set(getKey(cacheKey), obj);
  }

  public static final Object get(CacheKey cacheKey) {
    return SessionManager.get(getKey(cacheKey));
  }

  public static final void clear(CacheKey cacheKey) {
    SessionManager.clear(getKey(cacheKey));
  }

  public static final void clearCache() {
    clear(CacheKey.DEVICES);
    clear(CacheKey.CREDIT_CARDS);
    clear(CacheKey.ADDRESSES);
    clear(CacheKey.ACCOUNTS);
    clear(CacheKey.ACCOUNT_DETAIL);
  }

}