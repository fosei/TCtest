<link rel="stylesheet" href="<spring:url value="/static/styles/admin/controlBar.css" />" type="text/css" />
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/admin/controlBar.js" />"></script>

<div id="admin_control_bar">
  <!-- ADMIN LOGO -->
  <div class="logo">
    <a href="<spring:url value="/admin" />"> <img src="<spring:url value="/static/images/logo/logo_admin_sm.png" />" />
    </a>
  </div>

  <!-- LOGOUT/ID -->
  <div class="logout">
    <c:if test="${!empty sessionScope.admin}">
      <b>Administrator:</b>
      <c:out value="${sessionScope.admin.username}" />
    </c:if>
    <c:if test="${!empty sessionScope.manager}">
      <b>Manager:</b>
      <c:out value="${sessionScope.manager.username}" />
    </c:if>
    <a href="<spring:url value='/j_spring_security_logout' />">Logout</a>
  </div>

  <!-- CURRENTLY VIEWED USER -->
  <c:if test="${!empty sessionScope.user.email}">
    <div class="currentUser">
      <a href="<spring:url value="/account" />"><c:out value="${sessionScope.user.userId}" />: <c:out
          value="${sessionScope.user.email}" /> </a>
    </div>
  </c:if>

  <!-- SEARCH FORM -->
  <form id="adminControl" method="post" action="<spring:url value="/admin/user" />">
    <div style="float: left; padding-right: 5px;">
      <input name="admin_search_id" id="admin_search_id" type="text" class="hidden" /> <input autocomplete="off"
        name="admin_search_email" id="admin_search_email" type="text" title="Search by Email Address" />
      <div id="admin_search_results" class="search_results_box"></div>
    </div>
    <div style="float: left;">
      <a id="adminControlButton" href="#" class="button multi semi-s"><span>Go</span> </a> <a
        id="adminControlResetButton" href="#" class="button semi-s"><span>Reset</span> </a><input
        id="adminControlSubmit" class="hidden" type="submit" value="Go" /> <input id="adminControlReset" type="reset"
        class="hidden" />
    </div>
  </form>
</div>

<div style="height: 90px;"></div>