package com.trc.service.jms.message;

import java.io.Serializable;

import com.trc.user.User;
import com.tscp.mvne.NetworkInfo;

public class NetworkActivation implements Serializable {
  private static final long serialVersionUID = 3767440974367189996L;
  private User user;
  private NetworkInfo networkInfo;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public NetworkInfo getNetworkInfo() {
    return networkInfo;
  }

  public void setNetworkInfo(NetworkInfo networkInfo) {
    this.networkInfo = networkInfo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((networkInfo == null) ? 0 : networkInfo.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    NetworkActivation other = (NetworkActivation) obj;
    if (networkInfo == null) {
      if (other.networkInfo != null)
        return false;
    } else if (!networkInfo.equals(other.networkInfo))
      return false;
    if (user == null) {
      if (other.user != null)
        return false;
    } else if (!user.equals(other.user))
      return false;
    return true;
  }

}
