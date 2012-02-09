package com.trc.config;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.trc.service.gateway.TSCPMVNE;
import com.trc.util.logger.DevLogger;

@Component
public final class Config {
  private static ClassLoader classLoader;
  private static final String configFile = "config/config.properties";
  private static final String monthsFile = "config/dates/months.properties";
  private static final String yearsFile = "config/dates/years.properties";
  private static final String statesFile = "config/geo/states.properties";
  public static boolean production;
  public static boolean admin;
  private static int yearRange;
  public static SortedMap<String, String> states = new TreeMap<String, String>();
  public static SortedMap<String, String> months = new TreeMap<String, String>();
  public static SortedMap<Integer, String> yearsFuture = new TreeMap<Integer, String>();
  public static SortedMap<Integer, String> yearsPast = new TreeMap<Integer, String>();

  public static boolean initialized = false;

  private static org.slf4j.Logger logger = LoggerFactory.getLogger(Config.class);

  @PostConstruct
  public static void loadProperties() {
    if (!initialized) {
      classLoader = Config.class.getClassLoader();
      try {
        loadConfig();
        loadMonths();
        loadYears();
        loadStates();
        initialized = true;
      } catch (IOException e) {
        logger.error("Failed to load properties file: " + e.getMessage());
      }
    }
  }

  private static void loadConfig() throws IOException {
    if (!TSCPMVNE.initialized) {
      Properties props = new Properties();
      props.load(classLoader.getResourceAsStream(configFile));
      production = props.getProperty("production").equals("0") ? false : true;
      admin = props.getProperty("admin").equals("0") ? false : true;
      TSCPMVNE.serviceName = props.getProperty("serviceName");
      TSCPMVNE.namespace = props.getProperty("namespace");
      TSCPMVNE.location = production ? props.getProperty("wsdl_production_ip") : props.getProperty("wsdl");
      TSCPMVNE.initialized = true;
      DevLogger.debug("TSCPMVNE location set to " + TSCPMVNE.location);
    }
  }

  private static void loadMonths() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(monthsFile));
    for (Entry<Object, Object> entry : properties.entrySet()) {
      months.put((String) entry.getKey(), (String) entry.getValue());
    }
  }

  private static void loadYears() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(yearsFile));
    yearRange = Integer.parseInt(properties.getProperty("range"));
    int currentYear = new DateTime().getYear();
    String value;
    int yearFuture;
    int yearPast;
    for (int i = 0; i < yearRange; i++) {
      yearFuture = currentYear + i;
      value = Integer.toString(yearFuture).substring(2);
      yearsFuture.put(yearFuture, value);
      yearPast = currentYear - i;
      value = Integer.toString(yearPast).substring(2);
      yearsPast.put(yearPast, value);
    }
  }

  private static void loadStates() throws IOException {
    Properties properties = new Properties();
    properties.load(classLoader.getResourceAsStream(statesFile));
    for (Entry<Object, Object> entry : properties.entrySet()) {
      states.put((String) entry.getKey(), (String) entry.getValue());
    }
  }

}