package com.trc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SimpleDate {

  private static final DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
  private static final DateFormat shortDateFormat = new SimpleDateFormat("MMyy");

  public static synchronized String format(Date date) {
    return simpleDateFormat.format(date);
  }

  public static String getDate(Date date) {
    return format(date);
  }

  public static synchronized Date parse(String string) throws ParseException {
    return simpleDateFormat.parse(string);
  }

  public static synchronized Date parseShortDate(String string) throws ParseException {
    return shortDateFormat.parse(string);
  }

  public static void main(String[] args) {
    String date = "1311";
    Date simpleDate = null;
    int month = Integer.parseInt(date.substring(0, 2));
    int year = Integer.parseInt(date.substring(2));
    try {
      simpleDate = SimpleDate.parseShortDate(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    System.out.println(month);
    System.out.println(year);
    System.out.println(simpleDate);
  }
}
