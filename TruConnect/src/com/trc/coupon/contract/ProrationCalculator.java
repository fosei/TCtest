package com.trc.coupon.contract;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

public class ProrationCalculator {
  private static final double MRC = 4.99;
  private Calendar calendar = Calendar.getInstance();

  public int getDaysInMonth() {
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  public int getDaysInMonth(int month) {
    calendar.set(Calendar.MONTH, month - 1);
    int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    return days;
  }

  public int getCurrentDay() {
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    return day;
  }

  public float getProratedPercentage() {
    float days = getDaysInMonth();
    float day = getCurrentDay();
    float daysLeft = days - day;
    float percent = daysLeft / days;
    return percent;
  }

  public double getProratedAmount() {
    DecimalFormat df = new DecimalFormat("#.###");
    df.setRoundingMode(RoundingMode.HALF_UP);
    double amount = MRC * getProratedPercentage();
    return Double.parseDouble(df.format(amount));
  }

  @SuppressWarnings("unused")
  public static void main(String[] args) {
    ProrationCalculator pc = new ProrationCalculator();
    int days = pc.getDaysInMonth();
    int day = pc.getCurrentDay();
    int daysLeft = days - day;
    float percent = pc.getProratedPercentage();
    double amount = pc.getProratedAmount();
    System.out.println("Days in current month:\t\t" + days);
    System.out.println("Current day of month:\t\t" + day);
    System.out.println("Remaining percentage:\t\t" + percent);
    System.out.println("Amount to charge:\t\t" + amount);
  }
}