package com.trc.manager.webflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trc.exception.WebFlowException;
import com.trc.exception.management.DeviceManagementException;
import com.trc.manager.DeviceManager;
import com.trc.service.gateway.TruConnectUtil;
import com.trc.user.User;
import com.trc.web.flow.util.WebFlowUtil;
import com.tscp.mvne.Account;
import com.tscp.mvne.DeviceInfo;
import com.tscp.mvne.NetworkInfo;

@Component
public class DeviceFlowManager {
  @Autowired
  private DeviceManager deviceManager;

  private static final String ERROR_RESERVE_MDN = "An error occured while reserving your Mobile Data card. Please try entering your ESN again.";
  private static final String ERROR_ACTIVATE = "An error occured while activating your card. Please enter your ESN again.";
  private static final String ERROR_REMOVE_DEVICE = "An error occurred while clearing your device information. No changes were made. Please try again.";
  private static final String ERROR_DISCONNECT = "An error occurred while testing your device's ability to disconnect. No changes were made. Please try again.";
  private static final String ERROR_CREATE_SERVICE = "An error occurred while preparing your device's service. No changes were made. Please try again.";

  public void setDefaultDeviceLabel(DeviceInfo deviceInfo, String firstName) {
    deviceManager.setDefaultDeviceLabel(deviceInfo, firstName);
  }

  public void reserveMdn(NetworkInfo networkInfo) throws WebFlowException {
    try {
      NetworkInfo reservedNetworkInfo = deviceManager.reserveMdn();
      TruConnectUtil.copyNetworkInfo(networkInfo, reservedNetworkInfo);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_RESERVE_MDN);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void bindEsn(NetworkInfo networkInfo, DeviceInfo deviceInfo) {
    deviceManager.bindEsn(networkInfo, deviceInfo);
  }

  public void addDeviceInfo(DeviceInfo deviceInfo, Account account, User user) throws WebFlowException {
    try {
      DeviceInfo newDeviceInfo = deviceManager.addDeviceInfo(deviceInfo, account, user);
      TruConnectUtil.copyDeviceInfo(deviceInfo, newDeviceInfo);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void removeDeviceInfo(DeviceInfo deviceInfo, Account account, User user) throws WebFlowException {
    try {
      deviceManager.removeDeviceInfo(deviceInfo, account, user);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_REMOVE_DEVICE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void activateService(NetworkInfo networkInfo, User user) throws WebFlowException {
    try {
      NetworkInfo newNetworkInfo = deviceManager.activateService(networkInfo, user);
      TruConnectUtil.copyNetworkInfo(networkInfo, newNetworkInfo);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void deactivateService(Account account) throws WebFlowException {
    try {
      deviceManager.disconnectService(account);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_ACTIVATE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void disconnectFromKenan(Account account) throws WebFlowException {
    try {
      deviceManager.disconnectFromKenan(account, account.getServiceinstancelist().get(0));
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_DISCONNECT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void disconnectService(NetworkInfo networkInfo) throws WebFlowException {
    try {
      deviceManager.disconnectFromNetwork(networkInfo);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_DISCONNECT);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

  public void createServiceInstance(Account account, NetworkInfo networkInfo) throws WebFlowException {
    try {
      Account result = deviceManager.createServiceInstance(account, networkInfo);
      TruConnectUtil.copyAccount(account, result);
    } catch (DeviceManagementException e) {
      e.printStackTrace();
      WebFlowUtil.addError(ERROR_CREATE_SERVICE);
      throw new WebFlowException(e.getMessage(), e.getCause());
    }
  }

}
