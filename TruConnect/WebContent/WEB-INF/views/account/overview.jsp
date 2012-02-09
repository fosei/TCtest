<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/infoIconPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/overview.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Account Overview</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Account Activity</h3>
        <c:forEach var="accountDetail" items="${accountDetails}">
          <h4 style="float: left; display: inline-block">${accountDetail.deviceInfo.deviceLabel}</h4>
          <h4 style="float: right; display: inline-block">Current Balance: $${accountDetail.account.balance}</h4>
          <c:set var="currentBalance" value="${accountDetail.account.balance}" />
          <table>
            <tr>
              <th>Date and Time</th>
              <th>Type</th>
              <th style="text-align: right;">Usage</th>
              <th style="text-align: right;">Amount</th>
              <th style="text-align: right;">Balance</th>
              <th style="width: 16px;"></th>
            </tr>
            <c:forEach var="usageDetail" items="${accountDetail.usageHistory.recordsSummary}" varStatus="status">
              <%@ include file="/WEB-INF/includes/display/usageDetail.jsp"%>
            </c:forEach>
          </table>
          <div style="text-align: right; margin-bottom: 20px;">
            <a href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}" />">View More &raquo;</a>
          </div>

        </c:forEach>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Payments</h3>
        <h4>Last Payment</h4>
        <table>
          <tr>
            <th>Date and Time</th>
            <th>Type</th>
            <th>Account</th>
            <th style="text-align: right;">Amount</th>
            <th style="text-align: right;">Invoice</th>
          </tr>
          <c:forEach var="paymentRecord" items="${paymentHistory.newestRecord}">
            <%@ include file="/WEB-INF/includes/display/paymentRecord.jsp"%>
          </c:forEach>
        </table>
        <div style="text-align: right;">
          <a href="<spring:url value="/account/payment/history" />">View More &raquo;</a>
        </div>
        <div class="clear"></div>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>