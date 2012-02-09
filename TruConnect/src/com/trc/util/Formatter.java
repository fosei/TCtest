package com.trc.util;

public final class Formatter {

  public static String formatDollarAmount(Double inAmount) {
    return formatDollarAmount(Double.toString(inAmount), false);
  }

  public static String formatDollarAmountQuery(Double inAmount) {
    return formatDollarAmount(Double.toString(inAmount), true);
  }

  public static String formatDollarAmount(String inAmount, boolean query) {
    String[] splitAmount = inAmount.split("\\.");
    String dollars = "";
    String cents = "";
    if (splitAmount.length > 1) {
      dollars = splitAmount[0];
      cents = splitAmount[1];
      while (cents.length() != 2) {
        cents = cents + "0";
      }
    } else {
      dollars = inAmount;
    }
    if (query) {
      return dollars + cents;
    } else {
      return dollars + "." + cents;
    }
  }

  public static void main(String[] args) {
    String result = formatDollarAmountQuery(13.32);
    System.out.println(result);
  }
}
