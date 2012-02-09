package com.trc.manager;

import java.util.Collection;
import java.util.List;

import com.trc.exception.management.DeviceManagementException;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;

public interface DeviceManagerModel {

  public NetworkInfo reserveMdn() throws DeviceManagementException;

  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceManagementException;

  public Collection<DeviceInfo> getDeviceInfoList(User user) throws DeviceManagementException;

  public DeviceInfo addDeviceInfo(DeviceInfo deviceInfo, Account account, User user) throws DeviceManagementException;

  public List<DeviceInfo> removeDeviceInfo(DeviceInfo deviceInfo, Account account, User user)
      throws DeviceManagementException;

  public void updateDeviceInfo(User user, DeviceInfo deviceInfo) throws DeviceManagementException;

  public NetworkInfo swapDevice(User user, DeviceInfo oldDeviceInfo, DeviceInfo newDeviceInfo)
      throws DeviceManagementException;

  public NetworkInfo activateService(NetworkInfo networkInfo, User user) throws DeviceManagementException;

  public void suspendService(NetworkInfo networkInfo) throws DeviceManagementException;

  public void restoreService(NetworkInfo networkInfo) throws DeviceManagementException;

  public Account createServiceInstance(Account account, NetworkInfo networkInfo) throws DeviceManagementException;

  public void disconnectService(ServiceInstance serviceInstance) throws DeviceManagementException;

  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceManagementException;

  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceManagementException;

  public NetworkInfo reinstallCustomerDevice(User user, DeviceInfo deviceInfo) throws DeviceManagementException;
}