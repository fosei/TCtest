<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/ccValidation.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupCreditCardForms.js" />"></script>
<script type="text/javascript"
  src="<spring:url value="/static/javascript/pages/highlight/navigation/paymentMethods.js" />"></script>
</head>
<body onload="setExpirationDate('${creditCard.expirationDate}')">
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Payment Information</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Credit Card Information</h3>

        <form:form id="addCreditCard" cssClass="validatedForm" method="post" commandName="creditCard">

          <!-- Begin Errors -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.creditCard'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="nameOnCreditCard" />
                <form:errors path="creditCardNumber" />
                <form:errors path="verificationcode" />
                <form:errors path="expirationDate" />
                <form:errors path="address1" />
                <form:errors path="address2" />
                <form:errors path="city" />
                <form:errors path="state" />
                <form:errors path="zip" />
                <!-- Global Errors -->
                <spring:bind path="creditCard">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>
          <!-- End Errors -->

          <div class="row">
            <form:label path="isDefault">Default</form:label>
            <form:checkbox path="isDefault" value="Y" />
          </div>

          <div class="row" style="display: none">
            <form:label path="alias">Alias</form:label>
            <form:input path="alias" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
          </div>

          <div class="row">
            <form:label path="nameOnCreditCard" cssClass="required">Name on Card</form:label>
            <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="nameOnCreditCard" />
          </div>

          <div class="row">
            <form:label path="creditCardNumber" cssClass="required">
              <spring:message code="label.payment.cardNumber" />
            </form:label>
            <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="16"
              path="creditCardNumber" />
          </div>

          <div class="row">
            <form:label path="verificationcode" cssClass="required">Security Code</form:label>
            <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="4"
              path="verificationcode" cssStyle="width:60px;" />
            <a id="cvvInfo" href="#" style="margin-left: 10px;">What is this?</a> <span class="toolTip">This is
              the 3 digit code on the back of the card for Visa and Mastercard, or the 4 digit number on the front for
              American Express.<br /> <img src="<spring:url value="/static/images/creditCard/securityExample.png" />">
            </span>
          </div>

          <div class="row">
            <form:label path="expirationDate" cssClass="required">Expiration Date</form:label>
            <select id="monthSelect" style="width: 50px; margin-right: 10px;">
              <c:forEach var="month" items="${months}">
                <option value="${month.key}">${month.key}</option>
              </c:forEach>
            </select> <select id="yearSelect" style="width: 70px;">
              <c:forEach var="year" items="${years}">
                <option value="${year.value}">${year.key}</option>
              </c:forEach>
            </select>
            <form:input cssStyle="display:none;" cssClass="numOnly" maxLength="4"
              cssErrorClass="numOnly verificationFailed" path="expirationDate" />
          </div>

          <div id="creditCardImages" class="row pushed" style="height: 40px; line-height: 40px;">
            <img id="ImgAmex" style="vertical-align: middle;"
              src="<spring:url value='/static/images/creditCard/iconAmex.png' htmlEscape='true'/>" /> <img
              id="ImgMastercard" style="vertical-align: middle;"
              src="<spring:url value='/static/images/creditCard/iconMasterCard.png' htmlEscape='true'/>" /> <img
              id="ImgVisa" style="vertical-align: middle;"
              src="<spring:url value='/static/images/creditCard/iconVisa.png' htmlEscape='true'/>" /> <img
              id="ImgDiscover" style="vertical-align: middle;"
              src="<spring:url value='/static/images/creditCard/iconDiscover.png' htmlEscape='true'/>" />&nbsp;
          </div>

          <div class="clear"></div>
          <h3 style="margin-bottom: 10px; padding-bottom: 0px; padding-top: 10px; border-top: 1px #ccc solid;">Billing
            Address</h3>

          <div id="addressList" class="row">
            <!-- Create hidden fields for all the user's addresses and display them as an option-->
            <c:forEach items="${addresses}" var="address" varStatus="status">
              <input type="hidden" id="${status.index}_address1" value="${address.address1}" />
              <input type="hidden" id="${status.index}_address2" value="${address.address2}" />
              <input type="hidden" id="${status.index}_city" value="${address.city}" />
              <input type="hidden" id="${status.index}_state" value="${address.state}" />
              <input type="hidden" id="${status.index}_zip" value="${address.zip}" />
              <div class="address">
                <c:if test="${!empty address.address1}">
                  <div>${address.address1}</div>
                </c:if>
                <c:if test="${!empty address.zip}">
                  <div>${address.city}, ${address.state} ${address.zip}</div>
                </c:if>
                <div class="addressButtons">
                  <a href="#" class="button semi-s addressSelect" name="${status.index}"><span>Use This
                      Address</span> </a>
                </div>
              </div>
            </c:forEach>
          </div>

          <div class="clear"></div>
          <div id="selectBillingAddress" class="row hidden">
            <a id="selectExistingAddress" href="#" class="button semi-s" style="float: right;"><span>Select
                Existing Address</span> </a>
          </div>
          <div id="billingAddress" style="margin-top: 12px; padding-top: 12px;">
            <div class="row">
              <form:label path="address1" cssClass="required">Address 1</form:label>
              <form:input path="address1" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
            <div class="row">
              <form:label path="address2">Address 2</form:label>
              <form:input path="address2" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>
            <div class="row">
              <form:label path="city" cssClass="required">City</form:label>
              <form:input path="city" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
            </div>

            <div class="row">
              <form:label path="state" cssClass="required">State</form:label>
              <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;"
                path="state">
                <form:option value="0">
                  <spring:message code="label.selectOne" />
                </form:option>
                <c:forEach var="state" items="${states}">
                  <form:option value="${state.value}">${state.key}</form:option>
                </c:forEach>
              </form:select>
            </div>

            <div class="row">
              <form:label path="zip" cssClass="required">Billing Zip Code</form:label>
              <form:input path="zip" maxLength="5" cssClass="span-8 numOnly"
                cssErrorClass="span-8 numOnly validationFailed" />
            </div>
          </div>

          <div class="clear"></div>
          <!-- Buttons -->
          <div class="buttons">
            <a id="addCreditCardButton" href="#" class="button action-m"><span>Continue</span> </a> <input
              id="addCreditCardSubmit" type="submit" name="_eventId_submitAddCreditCard" value="Continue" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>