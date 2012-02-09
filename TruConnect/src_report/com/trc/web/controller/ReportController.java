package com.trc.web.controller;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.trc.config.Config;
import com.trc.manager.ActivationReportManager;
import com.trc.report.ActivationReport;
import com.trc.report.UserActivationReport;
import com.trc.util.logger.DevLogger;
import com.trc.util.logger.activation.ActivationState;
import com.trc.web.model.ResultModel;

@Controller
@RequestMapping("/admin/report")
public class ReportController {
  @Autowired
  private ActivationReportManager reportManager;

  @ModelAttribute
  private void dateReferenceData(ModelMap modelMap) {
    modelMap.addAttribute("states", Config.states.entrySet());
    modelMap.addAttribute("months", Config.months.entrySet());
    modelMap.addAttribute("years", Config.yearsPast.entrySet());
    int numDaysInMonth = new DateTime().dayOfMonth().getMaximumValue();
    int[] days = new int[numDaysInMonth];
    for (int i = 0; i < numDaysInMonth; i++) {
      days[i] = i + 1;
    }
    modelMap.addAttribute("days", days);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String showReports() {
    return "admin/report/reports";
  }

  @RequestMapping(value = "/activation", method = RequestMethod.GET)
  public String showActivaitonReportRequest() {
    return "admin/report/activation/request";
  }

  @RequestMapping(value = "/activation/{quickLink}", method = RequestMethod.GET)
  public ModelAndView getQuickView(@PathVariable String quickLink) {
    ResultModel model = new ResultModel("admin/report/activation/report");
    DateTime startDate = new DateTime();
    DateTime endDate = new DateTime();
    String period = null;
    if (quickLink.equals("lastMonth")) {
      startDate = startDate.minusMonths(1).dayOfMonth().withMinimumValue();
      endDate = startDate.dayOfMonth().withMaximumValue();
      period = "Last Month " + startDate.toString("MMMM yyyy");
    } else if (quickLink.equals("lastWeek")) {
      startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY).minusWeeks(1);
      endDate = startDate.withDayOfWeek(DateTimeConstants.SUNDAY);
      period = "Last Week " + startDate.toString("MMMM dd yyyy") + " to " + endDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("thisMonth")) {
      startDate = startDate.dayOfMonth().withMinimumValue();
      period = "This Month " + startDate.toString("MMMM yyyy");
    } else if (quickLink.equals("thisWeek")) {
      startDate = startDate.withDayOfWeek(DateTimeConstants.MONDAY);
      period = "This Week " + startDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("yesterday")) {
      startDate = startDate.minusDays(1);
      period = "Yesterday " + startDate.toString("MMMM dd yyyy");
    } else if (quickLink.equals("today")) {
      period = "Today " + startDate.toString("MMMM dd yyyy");
    }
    ActivationReport report = reportManager.getActivationReport(startDate, endDate);
    model.addObject("report", report);
    model.addObject("period", period);
    return model.getSuccess();
  }

  @RequestMapping(value = "/activation", method = RequestMethod.POST)
  public ModelAndView getActivationMap(@RequestParam("month_start") int monthStart,
      @RequestParam("day_start") int dayStart, @RequestParam("year_start") int yearStart,
      @RequestParam("month_end") int monthEnd, @RequestParam("day_end") int dayEnd,
      @RequestParam("year_end") int yearEnd) {
    ResultModel model = new ResultModel("admin/report/activation/report");
    DateTime startDate = new DateTime(yearStart, monthStart, dayStart, 0, 0);
    DateTime endDate = new DateTime(yearEnd, monthEnd, dayEnd, 23, 59);

    String period = "From " + startDate.toString("MM/dd/yyyy") + " to " + endDate.toString("MM/dd/yyyy");

    ActivationReport report = reportManager.getActivationReport(startDate, endDate);
    model.addObject("report", report);
    model.addObject("period", period);
    return model.getSuccess();
  }

  @RequestMapping(value = "/activation/user/{userId}", method = RequestMethod.GET)
  public ModelAndView getUserActivationReport(@PathVariable int userId) {
    ResultModel model = new ResultModel("admin/report/activation/user/report");
    UserActivationReport report = reportManager.getUserActivationReport(userId);
    model.addObject("report", report);
    return model.getSuccess();
  }

  private void printChildren(ActivationState actState) {
    for (ActivationState child : actState.getChildren()) {
      DevLogger.log(child.getActivationStateId().getActState().toString());
      printChildren(child);
    }
  }

}