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
    <div class="container">Manage Your Account</div>
  </div>

  <div class="container">
    <div id="main-content">

      <div class="span-13 colborder" style="min-height: 230px;">
        <h3>Login</h3>
        <form id="loginForm" action="<spring:url value='/j_spring_security_check' />" method="post">
          <div class="centerFloats">
            <ul>
              <c:if test="${not empty param.login_error}">
                <div class="alert error">
                  <h1>There was a problem logging in</h1>
                  <span style="color: #666;">&#8226; Username or Password was incorrect.</span>
                </div>
              </c:if>
              <li><input class="span-10" style="line-height: 22px; margin: 0px auto; float: none;" id="j_username"
                type="text" name="j_username" title="Email Address"
                value="<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false" /></c:if>" />
              </li>
              <li><input class="span-10" style="line-height: 22px; float: none; display: none;" id="j_password"
                type="password" name="j_password" />
              </li>
              <li><input class="span-10" style="line-height: 22px; float: none;" id="j_password_holder" type="text"
                name="j_password_holder" title="Password" value="Password" />
              </li>
              <li><div class="span-10" style="float: none;">
                  <a id="loginFormButton" href="#" class="button action-m" style="float: right;"><span>Sign
                      In</span> </a> <input id="loginFormSubmit" type="submit" class="hidden" /><a
                    href="<spring:url value='/retrieve/password' />" style="float: left;">Lost password</a>
                </div>
              </li>
            </ul>
          </div>
        </form>
      </div>

      <div class="span-10 last">
        <h3>New Users</h3>
        <p>Don't have an account yet? Purchased a TruConnect card? Activate your new device here.</p>
        <div class="centerFloats">
          <ul>
            <li><a href="<spring:url value="/activation" />" class="button action-m" style="float: left;"><span>Create
                  an Account</span> </a>
            </li>
          </ul>

        </div>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>