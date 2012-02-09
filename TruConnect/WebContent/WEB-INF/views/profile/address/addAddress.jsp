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
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Add Address</h3>
        <form:form id="editAddress" cssClass="validatedForm" method="post" commandName="address">

          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.address'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="address1" />
                <form:errors path="city" />
                <form:errors path="state" />
                <form:errors path="zip" />
                <spring:bind path="address">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <p>Add an additional address.</p>

          <div class="row hidden">
            <form:label path="addressId" cssClass="required">address ID</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="addressId" />
          </div>

          <div class="row hidden">
            <form:label path="label">Label</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="label" />
          </div>

          <div class="row">
            <form:label path="default">Default</form:label>
            <form:checkbox path="default" value="true" />
          </div>

          <div class="row">
            <form:label path="address1" cssClass="required">Address 1</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="address1" />
          </div>

          <div class="row">
            <form:label path="address2">Address 2</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="address2" />
          </div>

          <div class="row">
            <form:label path="city" cssClass="required">City</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="city" />
          </div>

          <div class="row">
            <form:label path="state" cssClass="required">State</form:label>
            <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;" path="state">
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
            <form:input cssClass="span-8 numOnly" maxLength="5" cssErrorClass="span-8 numOnly validationFailed"
              path="zip" />
          </div>

          <!-- Buttons -->
          <div class="buttons">
            <a id="editAddressButton" style="float: right;" href="#" class="button action-m"><span>Continue</span> </a><a
              style="float: right;" href="<spring:url value="/profile" />" class="button escape-m multi"><span>Cancel</span>
            </a> <input id="editAddressSubmit" type="submit" name="_eventId_submitEsn" value="Continue" class="hidden" />
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