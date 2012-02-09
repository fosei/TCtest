<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
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

        <c:if test="${updateEmailNotification}">
          <div class="info">
            <h1>An E-Mail has been sent to you</h1>
            <p>
              An email has been sent to
              <c:choose>
                <c:when test="${!empty newEmail}">
                  ${newEmail}
                </c:when>
                <c:otherwise>
                  ${user.email}
                </c:otherwise>
              </c:choose>
              to verify the changes to your account.
            </p>
          </div>
        </c:if>

        <c:if test="${profileUpdate}">
          <c:choose>
            <c:when test="${profileUpdateStatus}">
              <div class="success">
                <h1>${profileUpdateAttr} Updated</h1>
                <p>Your ${profileUpdateAttr} has been successfully updated!</p>
              </div>
            </c:when>
            <c:otherwise>
              <div class="error">
                <h1>${profileUpdateAttr} Not Updated</h1>
                <p>Your ${profileUpdateAttr} was not updated.</p>
              </div>
            </c:otherwise>
          </c:choose>
        </c:if>

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Login Information</h3>
        <div style="font-size: 15px;">

          <sec:authorize ifAnyGranted="ROLE_ADMIN, ROLE_MANAGER">
            <span class="span-6" style="line-height: 36px;">Status:</span>
            <span class="span-8" style="line-height: 36px;"> <c:choose>
                <c:when test="${user.enabled}">
                Enabled     
            
            </span>
            <a href="<spring:url value="/profile/user/disable"/>" class="button escape-s"
              onclick="return confirm('Do you want to disable ${user.email}?')"><span>Disable</span> </a>
            </c:when>
            <c:otherwise>Disabled</span>
              <a href="<spring:url value="/profile/user/enable"/>" class="button escape-s"
                onclick="return confirm('Do you want to enable ${user.email}?')"><span>Enable</span> </a>
            </c:otherwise>
            </c:choose>
            <div class="clear"></div>
          </sec:authorize>

          <span class="span-6" style="line-height: 36px;">E-Mail Address:</span> <span class="span-8"
            style="line-height: 36px;">${user.email}</span> <a href="<spring:url value="/profile/update/email"/>"
            class="button semi-s"><span>Change</span> </a>
          <div class="clear"></div>
          <span class="span-6" style="line-height: 36px;">Password:</span> <span class="span-8"
            style="line-height: 36px;">&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;</span> <a
            href="<spring:url value="/profile/update/password"/>" class="button semi-s"><span>Change</span> </a>
        </div>
        <div class="clear"></div>
        <h3 style="margin-bottom: 10px; padding-bottom: 0px; padding-top: 10px; border-top: 1px #ccc solid;">Addresses</h3>
        <c:forEach var="address" items="${addresses}">

          <div class="span-6 address">
            <c:if test="${address.default}">
              <div>
                <b>Default Address</b>
              </div>
            </c:if>
            <c:if test="${!empty address.address1}">
              <div>${address.address1}</div>
            </c:if>
            <c:if test="${!empty address.city}">
              <div>${address.city}, ${address.state} ${address.zip}</div>
            </c:if>
            <div class=addressButtons>
              <a href="<spring:url value="/profile/address/edit/${address.encodedAddressId}" />"
                class="button semi-s multi"><span>Edit</span> </a>
              <c:if test="${fn:length(addresses) > 1}">
                <a class="button semi-s"
                  href="<spring:url value="/profile/address/remove/${address.encodedAddressId}" />"><span>Remove</span>
                </a>
              </c:if>
            </div>
          </div>


        </c:forEach>
        <div class="span-6 address" style="color: #ccc;">
          <div class=addressButtons>
            <a href="<spring:url value="/profile/address/add" />" class="button semi-s multi"><span>Add New
                Address</span> </a>
          </div>
        </div>

        <c:forEach var="account" items="${accounts}">
          <div class="span-6 address hidden">
            <c:if test="${!empty account.firstname}">
              <div>${account.firstname} ${account.lastname}</div>
            </c:if>
            <c:if test="${!empty account.contactAddress1}">
              <div>${account.contactAddress1}</div>
            </c:if>
            <c:if test="${!empty account.contactCity}">
              <div>${account.contactCity}, ${account.contactState} ${account.contactZip}</div>
            </c:if>
            <div class=addressButtons>
              <a href="#" class="button semi-s multi"><span>Edit</span> </a> <a href="#" class="button semi-s"><span>Remove</span>
              </a>
            </div>
          </div>
        </c:forEach>
      </div>

      <div class="span-6 last sub-navigation">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <!-- Close main-content -->
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>
  <!-- Close container -->

</body>
</html>