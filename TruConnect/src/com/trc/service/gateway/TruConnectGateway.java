package com.trc.service.gateway;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;

import org.springframework.stereotype.Service;

import com.trc.config.Config;
import com.tscp.mvne.TruConnect;
import com.tscp.mvne.TruConnectService;

@Service
public class TruConnectGateway {
  private TruConnectService service;
  private TruConnect port;

  @PostConstruct
  public void init() {
    try {
      if (!TSCPMVNE.initialized) {
        Config.loadProperties();
      }
      String namespace = TSCPMVNE.namespace;
      String servicename = TSCPMVNE.serviceName;
      String location = TSCPMVNE.location;
      service = new TruConnectService(new URL(location), new QName(namespace, servicename));
    } catch (Exception e) {
      e.printStackTrace();
      service = new TruConnectService();
    }
    port = service.getTruConnectPort();
  }

  public TruConnect getPort() {
    return port;
  }
}