package com.trc.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.management.DeviceManagementException;
import com.trc.exception.service.DeviceServiceException;
import com.trc.service.DeviceService;
import com.trc.user.User;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.LogLevel;
import com.trc.util.logger.aspect.Loggable;
import com.trc.web.session.cache.CacheKey;
import com.trc.web.session.cache.CacheManager;
import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

@Service
public class DeviceManager implements DeviceManagerModel {
  @Autowired
  private DeviceService deviceService;

  @Loggable(value = LogLevel.TRACE)
  public void setDefaultDeviceLabel(DeviceInfo deviceInfo, String firstName) {
    deviceInfo.setDeviceLabel(firstName + "'s TruConnect Device");
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo reserveMdn() throws DeviceManagementException {
    try {
      return deviceService.reserveMdn();
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceManagementException {
    try {
      return deviceService.getNetworkInfo(esn, msid);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<DeviceInfo> getDeviceInfoList(User user) throws DeviceManagementException {
    List<DeviceInfo> deviceInfoList = getDevicesFromCache();
    if (deviceInfoList != null) {
      return deviceInfoList;
    } else {
      try {
        deviceInfoList = deviceService.getDeviceInfoList(user);
        saveDevicesToCache(deviceInfoList);
        return deviceInfoList;
      } catch (DeviceServiceException e) {
        throw new DeviceManagementException(e.getMessage(), e.getCause());
      }
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public DeviceInfo getDeviceInfo(User user, int deviceId) throws DeviceManagementException {
    try {
      List<DeviceInfo> deviceInfoList = getDeviceInfoList(user);
      for (DeviceInfo deviceInfo : deviceInfoList) {
        if (deviceInfo.getDeviceId() == deviceId)
          return deviceInfo;
      }
      return null;
    } catch (DeviceManagementException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo, Account account, User user) throws DeviceManagementException {
    try {
      deviceInfo.setDeviceId(0);
      deviceInfo.setCustId(user.getUserId());
      deviceInfo.setAccountNo(account.getAccountno());
      clearDevicesFromCache();
      return deviceService.addDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public List<DeviceInfo> removeDeviceInfo(DeviceInfo deviceInfo, Account account, User user)
      throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      return deviceService.deleteDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      e.printStackTrace();
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void updateDeviceInfo(User user, DeviceInfo deviceInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      deviceService.updateDeviceInfo(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo swapDevice(User user, DeviceInfo oldDeviceInfo, DeviceInfo newDeviceInfo)
      throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      NetworkInfo oldNetworkInfo = deviceService.getNetworkInfo(oldDeviceInfo.getDeviceValue(), null);
      NetworkInfo newNetworkInfo = deviceService.swapDevice(user, oldNetworkInfo, newDeviceInfo);
      return newNetworkInfo;
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo activateService(NetworkInfo networkInfo, User user) throws DeviceManagementException {
    try {
      return deviceService.activateService(user, networkInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void suspendService(NetworkInfo networkInfo) throws DeviceManagementException {
    ServiceInstance serviceInstance = new ServiceInstance();
    serviceInstance.setExternalid(networkInfo.getMdn());
    try {
      clearDevicesFromCache();
      deviceService.suspendService(serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void restoreService(NetworkInfo networkInfo) throws DeviceManagementException {
    ServiceInstance serviceInstance = new ServiceInstance();
    serviceInstance.setExternalid(networkInfo.getMdn());
    try {
      clearDevicesFromCache();
      deviceService.restoreService(serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public Account createServiceInstance(Account account, NetworkInfo networkInfo) throws DeviceManagementException {
    try {
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalid(networkInfo.getMdn());
      return deviceService.createServiceInstance(account, serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectService(ServiceInstance serviceInstance) throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      deviceService.disconnectService(serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void disconnectService(Account account) throws DeviceManagementException {
    try {
      disconnectService(account.getServiceinstancelist().get(0));
    } catch (DeviceManagementException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      deviceService.disconnectFromNetwork(networkInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceManagementException {
    try {
      deviceService.disconnectFromKenan(account, serviceInstance);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Override
  @Loggable(value = LogLevel.TRACE)
  public NetworkInfo reinstallCustomerDevice(User user, DeviceInfo deviceInfo) throws DeviceManagementException {
    try {
      clearDevicesFromCache();
      return deviceService.reinstallCustomerDevice(user, deviceInfo);
    } catch (DeviceServiceException e) {
      throw new DeviceManagementException(e.getMessage(), e.getCause());
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public boolean isDeviceAvailable(String esn) {
    try {
      NetworkInfo networkInfo = deviceService.getNetworkInfo(esn, null);
      DevLogger.log("isDeviceAvailable: " + esn + " received " + networkInfo);
      if (networkInfo != null && compareEsn(networkInfo, esn) && !isEsnInUse(networkInfo)) {
        return true;
      } else {
        return false;
      }
    } catch (DeviceServiceException e) {
      return false;
    }
  }

  @Loggable(value = LogLevel.TRACE)
  public void bindEsn(NetworkInfo networkInfo, DeviceInfo deviceInfo) {
    String esn = deviceInfo.getDeviceValue();
    if (isDec(esn)) {
      networkInfo.setEsnmeiddec(esn);
    } else if (isHex(esn)) {
      networkInfo.setEsnmeidhex(esn);
    }
  }

  public boolean compareEsn(DeviceInfo deviceInfo, NetworkInfo networkInfo) {
    String deviceEsn = deviceInfo.getDeviceValue();
    return compareEsn(networkInfo, deviceEsn);
  }

  private boolean compareEsn(NetworkInfo networkInfo, String esn) {
    DevLogger.log("comparEsn: " + networkInfo.getEsnmeiddec() + " " + networkInfo.getEsnmeidhex() + " " + esn);
    return networkInfo != null && (esn.equals(networkInfo.getEsnmeiddec()) || esn.equals(networkInfo.getEsnmeidhex()));
  }

  private boolean isEsnInUse(NetworkInfo networkInfo) {
    DevLogger.log("isEsnInUse: " + networkInfo.getStatus());
    return networkInfo.getStatus() != null
        && (networkInfo.getStatus().equals("A") || networkInfo.getStatus().equals("S") || networkInfo.getStatus()
            .equals("H"));
  }

  private static boolean isDec(String esn) {
    return esn.matches("\\d+") && (esn.length() == 11 || esn.length() == 18);
  }

  private static boolean isHex(String esn) {
    return !esn.matches("\\d+") && (esn.length() == 8 || esn.length() == 14);
  }

  /* *************************************************************
   * CacheManger helper functions
   * *************************************************************
   */

  @SuppressWarnings("unchecked")
  private List<DeviceInfo> getDevicesFromCache() {
    return (List<DeviceInfo>) CacheManager.get(CacheKey.DEVICES);
  }

  private void clearDevicesFromCache() {
    CacheManager.clear(CacheKey.DEVICES);
  }

  private void saveDevicesToCache(List<DeviceInfo> deviceInfoList) {
    CacheManager.set(CacheKey.DEVICES, deviceInfoList);
  }

}