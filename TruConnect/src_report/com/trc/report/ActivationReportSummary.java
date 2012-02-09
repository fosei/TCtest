package com.trc.report;

public class ActivationReportSummary {
  private int activated;
  private int uniqueReservations;
  private int failedReservations;
  private int successfulReservations;

  public int getActivated() {
    return activated;
  }

  public void setActivated(int activated) {
    this.activated = activated;
  }

  public int getUniqueReservations() {
    return uniqueReservations;
  }

  public void setUniqueReservations(int uniqueReservations) {
    this.uniqueReservations = uniqueReservations;
  }

  public int getFailedReservations() {
    return failedReservations;
  }

  public void setFailedReservations(int failedReservations) {
    this.failedReservations = failedReservations;
  }

  public int getSuccessfulReservations() {
    return successfulReservations;
  }

  public void setSuccessfulReservations(int successfulReservations) {
    this.successfulReservations = successfulReservations;
  }

  public int getNumUniqueUsers() {
    return getActivated() + getFailedReservations();
  }

}
