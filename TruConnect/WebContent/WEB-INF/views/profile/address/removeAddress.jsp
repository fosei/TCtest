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
    <div class="container">Profile</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Remove Address</h3>
        <p>Are you sure you want to remove the following address from your profile?</p>
        <form:form id="removeAddress" method="post" commandName="address">

          <p style="margin-left: 20px; font-weight: bold;">
            ${address.address1}<br />
            <c:if test="${!empty address.address2}">${address.address2}<br />
            </c:if>
            ${address.city}, ${address.state} ${address.zip}
          </p>

          <div class="hidden">
            <div class="row">
              <form:label path="addressId" cssClass="required">address ID</form:label>
              <form:input disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                path="addressId" />
            </div>

            <div class="row">
              <form:label path="label">Label</form:label>
              <form:input disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                path="label" />
            </div>

            <div class="row">
              <form:label path="address1" cssClass="required">Address 1</form:label>
              <form:input disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                path="address1" />
            </div>

            <div class="row">
              <form:label path="address2">Address 2</form:label>
              <form:input disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                path="address2" />
            </div>

            <div class="row">
              <form:label path="city" cssClass="required">City</form:label>
              <form:input disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                path="city" />
            </div>

            <div class="row">
              <form:label path="state" cssClass="required">State</form:label>
              <form:select disabled="disabled" cssClass="span-8 displayOnly" cssErrorClass="span-8 validationFailed"
                cssStyle="width:312px;" path="state">
                <form:option value="0">
                  <spring:message code="label.selectOne" />
                </form:option>
                <c:forEach var="state" items="${states}">
                  <form:option value="${state.value}">${state.key}</form:option>
                </c:forEach>
              </form:select>
            </div>

            <div class="row">
              <form:label path="zip" cssClass="required">Zip</form:label>
              <form:input disabled="disabled" cssClass="span-8 numOnly displayOnly" maxLength="5"
                cssErrorClass="span-8 numOnly validationFailed" path="zip" />
            </div>
          </div>

          <!-- Buttons -->
          <div class="buttons" style="float: right;">
            <a href="<spring:url value="/profile" />" class="button escape-m multi"><span>Cancel</span> </a> <a
              id="removeAddressButton" href="#" class="button action-m"><span>Remove</span> </a><input
              id="removeAddressSubmit" type="submit" value="Continue" class="hidden" />
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