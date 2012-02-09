package com.trc.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Paginator {
  private List records;
  private int currentPageNum = 1;
  private int pageSize = 20;
  private int summarySize;

  public Paginator() {
    // do nothing
  }

  public void setSummarySize(int summarySize) {
    this.summarySize = summarySize;
  }

  public int getSummarySize() {
    return this.summarySize;
  }

  public List getRecordsSummary() {
    return getRecords(0, summarySize);
  }

  public List getNewestRecord() {
    return getRecords(0, 1);
  }

  public List getRecords(int from, int to) {
    if (from < records.size()) {
      if (to < records.size()) {
        return records.subList(from, to);
      } else {
        return records.subList(from, records.size());
      }
    } else {
      return new ArrayList();
    }
  }

  public List getPage(int page) {
    if (page == 0) {
      return records;
    } else {
      int from = pageSize * (page - 1);
      int to = from + pageSize;
      return getRecords(from, to);
    }
  }

  public List getCurrentPage() {
    return getPage(getCurrentPageNum());
  }

  public int getPageCount() {
    int numPages = records.size() / getPageSize();
    int remainder = records.size() % getPageSize();
    if (remainder != 0) {
      return numPages + 1;
    } else {
      return numPages;
    }
  }

  public List getRecords() {
    return records;
  }

  public void setRecords(List records) {
    this.records = records;
  }

  public int getCurrentPageNum() {
    return currentPageNum;
  }

  public void setCurrentPageNum(int pageNum) {
    this.currentPageNum = pageNum;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
