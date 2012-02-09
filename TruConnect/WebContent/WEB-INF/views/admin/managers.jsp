<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Manager Control</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px; border-bottom: 1px #ccc dotted;">Service
          Representatives</h3>
        <div>
          <ul>
            <c:forEach var="manager" items="${managers}" varStatus="status">
              <li style="margin-top: 10px;">${manager.username} - <c:choose>
                  <c:when test="${manager.enabled}">enabled (<a
                      href="<spring:url value="/admin/managers/disable/${manager.userId}" />">disable</a>)</c:when>
                  <c:when test="${!manager.enabled}">disabled (<a
                      href="<spring:url value="/admin/managers/enable/${manager.userId}" />">enable</a>)</c:when>
                </c:choose>
              </li>
            </c:forEach>
          </ul>
        </div>
      </div>

      <sec:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="span-6 last sub-navigation">
          <%@ include file="/WEB-INF/includes/admin/navigation/adminNav.jsp"%>
        </div>
      </sec:authorize>
    </div>



    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>