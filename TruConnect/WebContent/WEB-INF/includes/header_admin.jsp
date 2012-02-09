<sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
  <%@ include file="/WEB-INF/includes/admin/control_bar.jsp"%>
</sec:authorize>

<div class="container">
  <div id="header">
    <!-- Begin Logo -->
    <div class="span-12">
      <div class="logo">
        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
          <a href="http://www.truconnect.com/"> <img src="<spring:url value='/static/images/logo_s1.jpg' />"
            alt="TruConnect Logo" /> </a>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
          <img src="<spring:url value='/static/images/logo_s1.jpg'/>" alt="TruConnect Logo" />
        </sec:authorize>
      </div>
    </div>
    <!-- End Logo -->

    <div class="span-12 last">
      <!-- Begin Secondary Navigation -->
      <div class="secondary-navigation">
        <ul>
          <sec:authorize ifNotGranted="ROLE_ADMIN">
            <sec:authorize ifNotGranted="ROLE_MANAGER">
              <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <li>Welcome ${user.contactInfo.firstName} ${user.contactInfo.lastName}</li>
              </sec:authorize>
              <c:if test="${empty param.login_error}">
                <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                  <li><a href="<spring:url value='/j_spring_security_logout' />">Logout</a></li>
                </sec:authorize>
              </c:if>
            </sec:authorize>
          </sec:authorize>
        </ul>
      </div>
      <!-- End Secondary Navigation -->

      <!-- Begin Navigation -->
      <div class="navigation">
        <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
          <ul>
            <li><a href="http://www.truconnect.com/">Home</a></li>
            <li><a href="http://www.truconnect.com/plans/">Plans</a></li>
            <li><a href="https://store.truconnect.com/">Devices</a></li>
            <li><a href="http://www.truconnect.com/support/">Support</a></li>
          </ul>
        </sec:authorize>
      </div>
    </div>
    <!-- End Navigation -->
  </div>
</div>
<div class="clear"></div>