<h3>Administration</h3>
<ul>
  <li id="nav_manageReps"><a href="<spring:url value="/admin/managers"/>">Manage Service Agents</a>
  </li>
  <sec:authorize ifAnyGranted="ROLE_ADMIN">
    <li id="nav_manageAdmin"><a href="<spring:url value="/admin/admins"/>">Manage Administrators</a></li>
    <li id="nav_createUser"><a href="<spring:url value="/admin/create" />">Create New Agent</a></li>
    <li id="nav_reports"><a href="<spring:url value="/admin/report" />">Reports</a></li>
  </sec:authorize>
</ul>