<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/mousePositionPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/infoIconPopup.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/activity.js" />"></script>
</head>
<body class="app">
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Account Activity</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <c:if test="${fn:length(accountDetails) > 0}">
        <div id="accountList" style="border:1px blue dashed; height:100px; width:700px;">
          <c:forEach var="accountDetail" items="${accountList}">
            <div style="height:20px; width:233px; float:left;">
              <input type="radio" name="account" value="${accountDetail.encodedAccountNum}" /><span>${accountDetail.deviceInfo.deviceLabel}</span>
            </div>

            <div class="span-6 hidden">
              <div style="font-weight: bold; margin-bottom: 10px;">${accountDetail.deviceInfo.deviceLabel}</div>
              <div class="clear"></div>
              <a href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}"/>" class="button semi-s"><span>View
                  Activity</span> </a>
            </div>
          </c:forEach>
          <div class="clear"></div>
          </div>
        </c:if>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">History</h3>
        <c:forEach var="accountDetail" items="${accountDetails}">
          <h4 style="float: left; display: inline-block;">${accountDetail.deviceInfo.deviceLabel}</h4>
          <h4 style="float: right; display: inline-block;">Current Balance: $${accountDetail.account.balance}</h4>
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
            <c:forEach var="usageDetail" items="${accountDetail.usageHistory.currentPage}">
              <%@ include file="/WEB-INF/includes/display/usageDetail.jsp"%>
            </c:forEach>
          </table>
          <c:set var="prevPageNum" value="${accountDetail.usageHistory.currentPageNum - 1}" />
          <c:set var="nextPageNum" value="${accountDetail.usageHistory.currentPageNum + 1}" />
          <c:if test="${prevPageNum > 0}">
            <span style="float: left"><a
              href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${prevPageNum}" />">&laquo;
                Previous Page</a> </span>
          </c:if>
          <c:if test="${accountDetail.usageHistory.currentPageNum < accountDetail.usageHistory.pageCount}">
            <span style="float: right"><a
              href="<spring:url value="/account/activity/${accountDetail.encodedAccountNum}/${nextPageNum}" />">Next
                Page &raquo;</a> </span>
          </c:if>
        </c:forEach>
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