<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript"
  src="<spring:url value="/static/javascript/pages/highlight/navigation/paymentHistory.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Payment History</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Payments</h3>
        <table>
          <tr>
            <th>Date and Time</th>
            <th>Type</th>
            <th>Account</th>
            <th style="text-align: right;">Amount</th>
            <th style="text-align: right;">Invoice</th>
          </tr>
          <c:forEach var="paymentRecord" items="${paymentHistory.currentPage}">
            <%@ include file="/WEB-INF/includes/display/paymentRecord.jsp"%>
          </c:forEach>
        </table>
        <c:set var="prevPageNum" value="${paymentHistory.currentPageNum - 1}" />
        <c:set var="nextPageNum" value="${paymentHistory.currentPageNum + 1}" />
        <c:if test="${prevPageNum > 0}">
          <span style="float: left"><a
            href="<spring:url value="/account/payment/history/${prevPageNum}" />">&laquo;
              Previous Page</a>
          </span>
        </c:if>
        <c:if test="${paymentHistory.currentPageNum < paymentHistory.pageCount}">
          <span style="float: right"><a
            href="<spring:url value="/account/payment/history/${nextPageNum}" />">Next
              Page &raquo;</a>
          </span>
        </c:if>
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