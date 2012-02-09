package com.trc.service;

import java.util.List;

import javax.xml.ws.WebServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trc.exception.service.DeviceServiceException;
import com.trc.service.gateway.TruConnectGateway;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;
import com.tscp.mvne.ServiceInstance;
import com.tscp.mvne.TruConnect;

@Service
public class DeviceService implements DeviceServiceModel {
  private TruConnect truConnect;

  @Autowired
  public void init(TruConnectGateway truConnectGateway) {
    this.truConnect = truConnectGateway.getPort();
  }

  @Override
  public NetworkInfo getNetworkInfo(String esn, String msid) throws DeviceServiceException {
    try {
      return truConnect.getNetworkInfo(esn, msid);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void updateDeviceInfo(User user, DeviceInfo deviceInfo) throws DeviceServiceException {
    try {
      truConnect.updateDeviceInfoObject(TruConnectUtil.toCustomer(user), deviceInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<DeviceInfo> getDeviceInfoList(User user) throws DeviceServiceException {
    try {
      return truConnect.getDeviceList(TruConnectUtil.toCustomer(user));
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public NetworkInfo swapDevice(User user, NetworkInfo oldNetworkInfo, DeviceInfo deviceInfo)
      throws DeviceServiceException {
    try {
      return truConnect.swapDevice(TruConnectUtil.toCustomer(user), oldNetworkInfo, deviceInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public NetworkInfo reserveMdn() throws DeviceServiceException {
    try {
      return truConnect.reserveMDN();
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public DeviceInfo addDeviceInfo(User user, DeviceInfo deviceInfo) throws DeviceServiceException {
    try {
      return truConnect.addDeviceInfoObject(TruConnectUtil.toCustomer(user), deviceInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public List<DeviceInfo> deleteDeviceInfo(User user, DeviceInfo deviceInfo) throws DeviceServiceException {
    try {
      return truConnect.deleteDeviceInfoObject(TruConnectUtil.toCustomer(user), deviceInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void suspendService(ServiceInstance serviceInstance) throws DeviceServiceException {
    try {
      truConnect.suspendService(serviceInstance);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void restoreService(ServiceInstance serviceInstance) throws DeviceServiceException {
    try {
      truConnect.restoreService(serviceInstance);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public NetworkInfo activateService(User user, NetworkInfo networkInfo) throws DeviceServiceException {
    try {
      return truConnect.activateService(TruConnectUtil.toCustomer(user), networkInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public Account createServiceInstance(Account account, ServiceInstance serviceInstance) throws DeviceServiceException {
    try {
      return truConnect.createServiceInstance(account, serviceInstance);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void disconnectService(ServiceInstance serviceInstance) throws DeviceServiceException {
    try {
      truConnect.disconnectService(serviceInstance);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void disconnectFromNetwork(NetworkInfo networkInfo) throws DeviceServiceException {
    try {
      truConnect.disconnectFromNetwork(networkInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public void disconnectFromKenan(Account account, ServiceInstance serviceInstance) throws DeviceServiceException {
    try {
      truConnect.disconnectServiceInstanceFromKenan(account, serviceInstance);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

  @Override
  public NetworkInfo reinstallCustomerDevice(User user, DeviceInfo deviceInfo) throws DeviceServiceException {
    try {
      return truConnect.reinstallCustomerDevice(TruConnectUtil.toCustomer(user), deviceInfo);
    } catch (WebServiceException e) {
      throw new DeviceServiceException(e.getMessage(), e.getCause());
    }
  }

}
