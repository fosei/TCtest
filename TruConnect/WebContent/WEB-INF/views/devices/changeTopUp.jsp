<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/devices.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manage Devices</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 20px; padding-bottom: 0px;">Change Top-Up Amount</h3>
        <p>When the balance for this device runs low, it will be topped up with the amount selected below.</p>

        <h4 style="float: left; display: inline-block">${accountDetail.deviceInfo.deviceLabel}
          (${accountDetail.deviceInfo.deviceStatus})</h4>
        <h4 style="float: right; display: inline-block">Current Balance: $${accountDetail.account.balance}</h4>
        <div class="clear"></div>
        <form:form id="topUp" commandName="accountDetail" method="POST" cssClass="validatedForm">

          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.accountDetail'].allErrors}">
            <div class="row">
              <div class="alert error">
                <form:errors path="topUp" />
                <spring:bind path="accountDetail">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <form:radiobutton path="topUp" value="10.00" />$10.00<br />
          <form:radiobutton path="topUp" value="20.00" />$20.00<br />
          <form:radiobutton path="topUp" value="30.00" />$30.00<br />
          <div class="clear"></div>
          <div class="buttons">
            <a id="topUpButton" href="#" onclick="$('#topUpSubmit').click()" class="button action-m"><span>Update</span>
            </a> <a href="<spring:url value="/devices" />" class="button escape-m multi"><span>Cancel</span> </a> <input
              id="topUpSubmit" type="submit" class="hidden" value="Update" />
          </div>
        </form:form>

      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>