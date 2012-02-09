<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Activation Report</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Date Range:</h3>

        <form id="activationReport" class="validatedForm" method="POST">

          <div class="row">
          <a href="<spring:url value="/admin/report/activation/lastMonth" />" >Last Month</a> | 
          <a href="<spring:url value="/admin/report/activation/thisMonth" />" >This Month</a> | 
          <a href="<spring:url value="/admin/report/activation/lastWeek" />" >Last Week</a> | 
          <a href="<spring:url value="/admin/report/activation/thisWeek" />" >This Week</a> | 
          <a href="<spring:url value="/admin/report/activation/yesterday" />" >Yesterday</a> | 
          <a href="<spring:url value="/admin/report/activation/today" />" >Today</a></div>

          <div class="row">
            <label for="startDate" class="required">Start Date</label> <select name="month_start"
              style="width: 50px; margin-right: 10px;">
              <c:forEach var="month" items="${months}">
                <option value="${month.key}">${month.key}</option>
              </c:forEach>
            </select> <select name="day_start" style="width: 50px; margin-right: 10px;">
              <c:forEach var="day" items="${days}">
                <option value="${day}">${day}</option>
              </c:forEach>
            </select> <select name="year_start" style="width: 70px;">
              <c:forEach var="year" items="${years}">
                <option value="${year.key}">${year.key}</option>
              </c:forEach>
            </select>
          </div>

          <div class="row">
            <label for="endDate" class="required">End Date</label> <select name="month_end"
              style="width: 50px; margin-right: 10px;">
              <c:forEach var="month" items="${months}">
                <option value="${month.key}">${month.key}</option>
              </c:forEach>
            </select> <select name="day_end" style="width: 50px; margin-right: 10px;">
              <c:forEach var="day" items="${days}">
                <option value="${day}">${day}</option>
              </c:forEach>
            </select> <select name="year_end" style="width: 70px;">
              <c:forEach var="year" items="${years}">
                <option value="${year.key}">${year.key}</option>
              </c:forEach>
            </select>
          </div>

          <div class="buttons">
            <a href="#" id="activationReportButton" class="button action-m"><span>Get Report</span> </a> <input
              id="activationReportSubmit" class="hidden" type="submit" value="Get Report" />
          </div>

        </form>

      </div>

      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="span-6 last sub-navigation">
          <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
        </div>
      </sec:authorize>
    </div>

    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>