<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/navigation/profile.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manage Profile</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Change E-Mail Address</h3>

        <form:form id="updateEmail" method="POST" action="${formAction}" commandName="updateEmail"
          cssClass="validatedForm">
          <!--Begin Error Display -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.updateEmail'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="oldPassword" />
                <form:errors path="email" />
                <form:errors path="confirmEmail" />
              </div>
            </div>
          </c:if>
          <!--End Error Display -->

          <div class="row">
            <form:label path="oldPassword" cssClass="required">Password</form:label>
            <form:password path="oldPassword" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="email" cssClass="required">New E-Mail Address</form:label>
            <form:input path="email" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="confirmEmail" cssClass="required">Confirm E-Mail Address</form:label>
            <form:input path="confirmEmail" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="updateEmailButton" href="#" class="button action-m"><span>Update E-Mail Address</span> </a> <a
              href="<spring:url value="/profile" />" class="button escape-m multi"><span>Cancel</span> </a> <input
              id="updateEmailSubmit" type="submit" value="Update E-Mail Address" class="hidden" />
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