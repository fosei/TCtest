<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/paymentMethod.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/selectPaymentMethod.js" />"></script>
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

        <a id="addNewButton" href="#" onclick="$('#choosePaymentMethodNewSubmit').click()" class="button action-m" style="float: right;"><span>Add New Card</span> </a>
        <h3 style="margin-bottom: 40px; padding-bottom: 0px;">Credit Cards</h3>

        <form:form id="choosePaymentMethod" cssClass="validatedForm" method="post" commandName="creditCard">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.creditCard'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="nameOnCreditCard" />
                <form:errors path="creditCardNumber" />
                <form:errors path="verificationcode" />
                <form:errors path="expirationDate" />
                <form:errors path="address1" />
                <form:errors path="address2" />
                <form:errors path="city" />
                <form:errors path="state" />
                <form:errors path="zip" />
                <spring:bind path="creditCard">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <c:forEach var="creditCard" items="${paymentMethods}" varStatus="status">
            <c:if test="${creditCard.isDefault == 'Y'}">
              <div style="font-size: 16px; line-height: 24px; font-weight: bold;">Default Method</div>
            </c:if>
            <div style="font-size: 16px; line-height: 24px;">${creditCard.nameOnCreditCard}</div>
            <div style="font-size: 16px; line-height: 24px;">${creditCard.creditCardNumber}</div>
            <div style="font-size: 16px; line-height: 24px; float: left;">${creditCard.city}, ${creditCard.state}
              ${creditCard.zip}</div>
            <div style="float: right">
              <a href="#" class="button semi-s paymentSelect" name="${creditCard.paymentid}" style="float: right;"><span>Select</span>
              </a>
            </div>
            <div style="clear: both; border-bottom: 1px dotted #cccccc; height: 30px; margin-bottom: 20px;"></div>
          </c:forEach>

          <form:input path="paymentid" cssClass="hidden" />
          <input id="choosePaymentMethodSelectSubmit" class="hidden" type="submit" name="_eventId_select" value="Select" />
          <input id="choosePaymentMethodNewSubmit" class="hidden" type="submit" name="_eventId_new" value="Create New" />

        </form:form>

      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/progress/addDeviceProgress.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>