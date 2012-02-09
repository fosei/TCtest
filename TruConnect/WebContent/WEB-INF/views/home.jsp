<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupLoginForms.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Service Plan Activation</div>
  </div>

  <div class="container">
    <div id="main-content">

      <div class="span-15">
        <h3>New Users</h3>
        <p>If you have received your TruConnect device you can activate your service plan on these pages. Start by
          creating a new user account.</p>
        <div class="centerFloats">
          <ul>
            <li><a class="button action-l" href="<spring:url value="/activation" />"><span>Create
                  Account</span>
            </a>
            </li>
          </ul>
        </div>
      </div>

      <div class="span-9 colborderleft last">
        <h3>Existing Users</h3>
        <p>If you already have a TruConnect account you can activate a new device or replace an existing device.
          Sign in to get started.</p>
        <form id="loginForm" action="<spring:url value='/j_spring_security_check' />" method="post">
          <div>
            <c:if test="${not empty param.login_error}">
              <div>
                Login failed, please try again. <span style="color: red;"><c:out
                    value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.</span>
              </div>
            </c:if>
            <input class="span-9" style="line-height: 22px;" id="j_username" type="text" name="j_username"
              title="Email Address"
              value="<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>" />
            <input class="span-9" style="line-height: 22px; display: none;" id="j_password" type="password"
              name="j_password" /> <input class="span-9" style="line-height: 22px;" id="j_password_holder" type="text"
              name="j_password_holder" title="Password" value="Password" />
            <div class="span-9">
              <a id="loginFormButton" href="#" class="button action-m" style="float: right;"><span>Sign In</span>
              </a> <input id="loginFormSubmit" type="submit" class="hidden" /> <a
                href="<spring:url value='/retrieve/password' />" style="float: left;">Lost password</a>
            </div>
          </div>
        </form>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>