<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">
      Activation Report: <span style="color: #666;">${period}</span>
    </div>
  </div>

  <script type="text/javascript">
			$(function() {
				$("img.expand_report_detail").click(function() {
					var divDetail = $("#activationReportDetail").slideToggle();
				});
			});
		</script>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Summary</h3>
        <div id="activationReportSummary">
          Activated: ${report.summary.activated}<br /> Unique Reservations: ${report.summary.uniqueReservations}<br />
          Failed Reservations: ${report.summary.failedReservations}<br /> Successful Reservations:
          ${report.summary.successfulReservations}
        </div>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">
          Details
          <sec:authorize ifAnyGranted="ROLE_ADMIN">
            <img class="expand_report_detail" style="margin-right: 5px;"
              src="<spring:url value="/static/images/buttons/icons/add.png" />" />
          </sec:authorize>
        </h3>

        <div class="hidden" id="activationReportDetail"
          style="border: 1px gray dashed; padding: 0px 8px 10px 8px; background: #efefff; display: none;">
          <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Failed States:</h3>
          <c:if test="${fn:length(report.failedStates) < 1}">None</c:if>
          <c:forEach var="state" items="${report.failedStates}">
          ${state.key}: ${state.value}<br />
          </c:forEach>

          <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Activated Users:
            ${fn:length(report.activatedUsers)}</h3>
          <c:if test="${fn:length(report.activatedUsers) < 1}">None</c:if>
          <c:forEach var="user" items="${report.activatedUsers}">
          <a style="color:black;" style="text-decoration:none;" href="<spring:url value="/admin/report/activation/user/${user.userId}" />">${user.userId}: ${user.username}</a><br />
          </c:forEach>

          <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Unique
            Reservations: ${fn:length(report.uniqueReservations)}</h3>
          <c:if test="${fn:length(report.uniqueReservations) < 1}">None</c:if>
          <c:forEach var="user" items="${report.uniqueReservations}">
          <a style="color:black;" href="<spring:url value="/admin/report/activation/user/${user.userId}" />">${user.userId}: ${user.username}</a><br />
          </c:forEach>

          <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Failed
            Reservations: ${fn:length(report.failedReservations)}</h3>
          <c:if test="${fn:length(report.failedReservations) < 1}">None</c:if>
          <c:forEach var="user" items="${report.failedReservations}">
          <a style="color:black;" href="<spring:url value="/admin/report/activation/user/${user.userId}" />">${user.userId}: ${user.username}</a><br />
          </c:forEach>

          <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Successful
            Reservations: ${fn:length(report.successfulReservations)}</h3>
          <c:if test="${fn:length(report.successfulReservations) < 1}">None</c:if>
          <c:forEach var="user" items="${report.successfulReservations}">
          <a style="color:black;" href="<spring:url value="/admin/report/activation/user/${user.userId}" />">${user.userId}: ${user.username}</a><br />
          </c:forEach>
        </div>
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