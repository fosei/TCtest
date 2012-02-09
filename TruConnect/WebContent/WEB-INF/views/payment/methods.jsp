<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript"
  src="<spring:url value="/static/javascript/pages/highlight/navigation/paymentMethods.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Payment Methods</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <c:if test="${fn:length(paymentMethods) < 1}">
          <a href="<spring:url value="/account/payment/methods/add" />" class="button action-m" style="float: right;"><span>Add
              New Card</span> </a>
        </c:if>
        <h3 style="margin-bottom: 40px; padding-bottom: 0px;">Credit Cards</h3>

        <c:forEach var="creditCard" items="${paymentMethods}" varStatus="status">
          <c:if test="${creditCard.isDefault == 'Y'}">
            <div style="font-size: 16px; line-height: 24px; font-weight: bold;">Default Method</div>
          </c:if>
          <div style="font-size: 16px; line-height: 24px;">${creditCard.nameOnCreditCard}</div>
          <div style="font-size: 16px; line-height: 24px;">${creditCard.creditCardNumber}</div>
          <div style="font-size: 16px; line-height: 24px; float: left;">${creditCard.city}, ${creditCard.state}
            ${creditCard.zip}</div>
          <div style="float: right;">
            <c:if test="${fn:length(paymentMethods) > 1}">
              <a href="<spring:url value="/account/payment/methods/remove/${encodedPaymentIds[status.index]}" />"
                class="button semi-s" style="float: right;"><span>Remove</span> </a>
            </c:if>
            <a href="<spring:url value="/account/payment/methods/edit/${encodedPaymentIds[status.index]}" />"
              class="button semi-s multi" style="float: right;"><span>Edit</span> </a>
          </div>
          <div style="clear: both; border-bottom: 1px dotted #cccccc; height: 30px; margin-bottom: 20px;"></div>
        </c:forEach>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>