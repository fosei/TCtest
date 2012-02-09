<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/jCaptcha.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addUserInfo.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addUserInfo.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Account Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Login Information</h3>

        <form:form id="userInfo" cssClass="validatedForm" method="post" commandName="registration">
          <!-- Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.registration'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="user.contactInfo.firstName" />
                <form:errors path="user.contactInfo.lastName" />
                <form:errors path="user.username" />
                <form:errors path="user.password" />
                <form:errors path="confirmPassword" />
                <form:errors path="user.email" />
                <form:errors path="confirmEmail" />
                <form:errors path="user.userHint.hintId" />
                <form:errors path="user.userHint.hintAnswer" />
                <form:errors path="jcaptcha" />
                <!-- Global Errors -->
                <spring:bind path="registration">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Form Description/Message -->
          <p>If you have received your TruConnect device you can activate your service plan on these pages. Start by
            creating a new user account.</p>

          <!-- First/Last Name -->
          <div class="row">
            <form:label path="user.contactInfo.firstName" cssClass="required">First Name </form:label>
            <form:input path="user.contactInfo.firstName" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>
          <div class="row">
            <form:label path="user.contactInfo.lastName" cssClass="required">Last Name </form:label>
            <form:input path="user.contactInfo.lastName" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <!-- Username -->
          <div class="row hidden">
            <form:label path="user.username" cssClass="required">Choose a Username </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="user.username" />
          </div>

          <!-- Email -->
          <div class="row">
            <form:label path="user.email" cssClass="required">
              <spring:message code="label.email" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="user.email" />
          </div>
          <div class="row">
            <form:label path="confirmEmail" cssClass="required">
              <spring:message code="label.confirmEmail" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="confirmEmail" />
          </div>

          <!-- Password -->
          <div class="row">
            <form:label path="user.password" cssClass="required">
              <spring:message code="label.password" />
            </form:label>
            <form:password cssClass="span-8" cssErrorClass="span-8 validationFailed" path="user.password" />
          </div>
          <div class="row">
            <form:label path="confirmPassword" cssClass="required">
              <spring:message code="label.confirmPassword" />
            </form:label>
            <form:password cssClass="span-8" cssErrorClass="span-8 validationFailed" path="confirmPassword" />
          </div>

          <!-- Security question -->
          <div class="row">
            <form:label path="user.userHint.hintId" cssClass="required">Security Question </form:label>
            <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;"
              path="user.userHint.hintId">
              <form:option value="0">
                <spring:message code="label.selectOne" />
              </form:option>
              <c:forEach var="hint" items="${hints}">
                <form:option value="${hint.hintId}">
                  <c:out value="${hint.hintQuestion}" />
                </form:option>
              </c:forEach>
            </form:select>
          </div>
          <div class="row">
            <form:label path="user.userHint.hintAnswer" cssClass="required">
              <spring:message code="label.hintAnswer" />
            </form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="user.userHint.hintAnswer" />
          </div>

          <!-- Jcaptcha -->
          <div class="row" style="margin-bottom: 0px; padding-bottom: 0px;">
            <form:label path="jcaptcha" cssClass="required">Word Verification </form:label>
            <div style="border: 1px #bbb solid; width: 310px; text-align: center; float: left;">
              <span style="color: #666; float: left; margin-left: 5px;">Enter the text in the image below</span> <img
                id="jCaptchaImage" src="<spring:url value='/static/images/jcaptcha.jpg' htmlEscape='true' />"
                alt="Security image" />
            </div>
          </div>
          <div class="row pushed" style="margin-top: -5px; padding-top: 0px;">
            <div style="width: 300px; text-align: right;">
              <a href="#" onclick="reloadJCaptchaImage('<spring:url value="/static/images/jcaptcha.jpg" />')"
                tabindex="-1">request another image</a>
            </div>
          </div>
          <div class="row pushed">
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" autocomplete="off" path="jcaptcha" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="userInfoButton" href="#" class="button action-m multi"><span>Continue</span> </a> <input
              id="userInfoSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
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