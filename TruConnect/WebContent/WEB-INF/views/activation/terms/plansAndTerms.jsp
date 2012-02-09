<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addDevice.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Plan Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <!-- Begin Left Column -->
      <div class="span-18 colborder">

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Serial Number</h3>

        <form:form id="plansAndTerms" cssClass="validatedForm" method="post" commandName="termsAndConditions">

          <!-- Begin Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.termsAndConditions'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="accept" />
                <!-- Global Errors -->
                <spring:bind path="termsAndConditions">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Display Device Info -->
          <p>
            <span class="span-6">Serial Number (ESN):</span>${deviceInfo.deviceValue} (${deviceInfo.deviceLabel})
          </p>

          <!-- Display Plans -->
          <div class="box">
            <h3>TruConnect Data Plan</h3>
            <div class="span-6">
              <ul style="list-style: none; font-weight: bold;">
                <li style="padding: 0; margin: 0; text-align: right; float: right; width: 100%;">$4.99 per month</li>
                <li style="padding: 0; margin: 0; text-align: right; float: right; width: 100%;">3.9&cent; per Mb</li>
              </ul>
            </div>
            <div class="span-11 colborderleft last">
              <ul style="margin: 0; padding-top: 0; font-size: 13px;">
                <li>Your TruConnect Data Plan has a balance, just like a pre-paid plan.</li>
                <li>If you balance drops below $2.00, it will automatically top up.</li>
                <li>A valid credit card is required to keep your plan active.</li>
                <li>The monthly access fee is paid automatically from your balance.</li>
                <li>There is no contract, you can stop your plan at any time.</li>
              </ul>
            </div>
            <!-- Display Terms -->
            <div class="clear"></div>
            <div class="terms"><%@ include file="/WEB-INF/views/activation/terms/registrationTos.jsp"%></div>
          </div>
          <!-- Term Acceptance -->
          <div class="row">
            <form:checkbox path="accept" cssErrorClass="validationFailed" />
            <span onclick="$('#accept1').click()">I have read and understand the terms and conditions.</span>
          </div>
          <div class="buttons">
            <a id="plansAndTermsButton" href="#" class="button action-m"><span>Continue</span> </a> <input
              id="plansAndTermsSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/progress/activationProgress.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>