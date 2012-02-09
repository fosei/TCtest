<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/ccValidation.js"/>"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupCreditCardPaymentForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCreditCardPayment.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>
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

        <form:form id="addCreditCard" cssClass="validatedForm" method="post" commandName="creditCardPayment">

          <!-- Begin Errors -->
          <c:if
            test="${not empty requestScope['org.springframework.validation.BindingResult.creditCardPayment'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="creditCard.nameOnCreditCard" />
                <form:errors path="creditCard.creditCardNumber" />
                <form:errors path="creditCard.verificationcode" />
                <form:errors path="creditCard.expirationDate" />
                <form:errors path="creditCard.address1" />
                <form:errors path="creditCard.address2" />
                <form:errors path="creditCard.city" />
                <form:errors path="creditCard.state" />
                <form:errors path="creditCard.zip" />
                <form:errors path="coupon.couponCode" />
                <form:errors path="coupon.startDate" />
                <form:errors path="coupon.endDate" />
                <form:errors path="coupon.quantity" />
                <spring:bind path="creditCardPayment">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <!-- Coupon -->
          <h3 style="padding: 10px 0px 10px 0px; border-top: 1px #ccc solid;">Coupons</h3>
          <div>
            <p>If you have received a coupon code, enter the coupon code below.</p>
            <div class="row">
              <form:label cssClass="required" path="coupon.couponCode">Coupon Code</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="coupon.couponCode" />
            </div>
            <div class="row pushed">
              <span id="couponMessage"></span>
            </div>
          </div>

          <h3 title="Credit Card Information" style="margin-bottom: 10px; padding-bottom: 0px;">Credit Card
            Information</h3>
          <div>
            <!-- Form Description/Message -->
            <p>You will be charged $10 which will be applied to the balance in your account. Every time your balance
              drops below $2, we will automatically add another $10 to your account, which will be charged to this
              credit card.</p>

            <div class="row hidden">
              <form:label path="creditCard.isDefault" cssClass="required">Default</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="creditCard.isDefault" value="Y" />
            </div>

            <div class="row">
              <form:label path="creditCard.nameOnCreditCard" cssClass="required">Name on Card</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="creditCard.nameOnCreditCard" />
            </div>

            <div class="row">
              <form:label path="creditCard.creditCardNumber" cssClass="required">
                <spring:message code="label.payment.cardNumber" />
              </form:label>
              <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="16"
                path="creditCard.creditCardNumber" />
            </div>

            <div class="row">
              <form:label path="creditCard.verificationcode" cssClass="required">Security Code</form:label>
              <form:input cssClass="span-8 numOnly" cssErrorClass="span-8 numOnly validationFailed" maxLength="4"
                path="creditCard.verificationcode" cssStyle="width:60px;" />
              <a id="cvvInfo" href="#" style="margin-left: 10px;" tabindex="-1">What is this?</a> <span class="toolTip">This
                is the 3 digit code on the back of the card for Visa and Mastercard, or the 4 digit number on the front
                for American Express.<br /> <img
                src="<spring:url value="/static/images/creditCard/securityExample.png" />"> </span>
            </div>

            <div class="row">
              <label class="required">Expiration Date</label> <select id="monthSelect"
                style="width: 50px; margin-right: 10px;">
                <option value="01">01</option>
                <option value="02">02</option>
                <option value="03">03</option>
                <option value="04">04</option>
                <option value="05">05</option>
                <option value="06">06</option>
                <option value="07">07</option>
                <option value="08">08</option>
                <option value="09">09</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
              </select> <select id="yearSelect" style="width: 70px;">
                <option value="11">2011</option>
                <option value="12">2012</option>
                <option value="13">2013</option>
                <option value="14">2014</option>
                <option value="15">2015</option>
                <option value="16">2016</option>
                <option value="17">2017</option>
                <option value="18">2018</option>
                <option value="19">2019</option>
                <option value="20">2020</option>
                <option value="21">2021</option>
                <option value="22">2022</option>
                <option value="23">2023</option>
                <option value="24">2024</option>
                <option value="25">2025</option>
                <option value="26">2026</option>
                <option value="27">2027</option>
                <option value="28">2028</option>
                <option value="29">2029</option>
                <option value="30">2030</option>
              </select>
            </div>

            <div class="row hidden">
              <form:label path="creditCard.expirationDate">Expiration Date (MMYY)</form:label>
              <form:input cssClass="numOnly" maxLength="4" cssErrorClass="numOnly verificationFailed"
                path="creditCard.expirationDate" />
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
          </div>

          <!-- Billing Address -->
          <div class="clear"></div>
          <h3 title="Billing Address" style="padding: 10px 0px 5px 0px; border-top: 1px #ccc solid;">Billing
            Address</h3>
          <div>

            <!-- Create hidden fields for all the user's addresses and display them as an option-->
            <c:if test="${not empty addresses}">
              <div id="addressList" class="row">
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
            </c:if>

            <div class="clear"></div>
            <div id="billingAddress" style="margin-top: 12px; padding-top: 12px;">
              <div class="row">
                <form:label path="creditCard.address1" cssClass="required">Address 1</form:label>
                <form:input path="creditCard.address1" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="creditCard.address2">Address 2</form:label>
                <form:input path="creditCard.address2" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="creditCard.city" cssClass="required">City</form:label>
                <form:input path="creditCard.city" cssClass="span-8" cssErrorClass="span-8 validationFailed" />
              </div>
              <div class="row">
                <form:label path="creditCard.state" cssClass="required">State</form:label>
                <form:select cssClass="span-8" cssErrorClass="span-8 validationFailed" cssStyle="width:312px;"
                  path="creditCard.state">
                  <form:option value="0">
                    <spring:message code="label.selectOne" />
                  </form:option>
                  <form:option value="AL">Alabama</form:option>
                  <form:option value="AK">Alaska</form:option>
                  <form:option value="AZ">Arizona</form:option>
                  <form:option value="AR">Arkansas</form:option>
                  <form:option value="CA">California</form:option>
                  <form:option value="CO">Colorado</form:option>
                  <form:option value="CT">Connecticut</form:option>
                  <form:option value="DC">District of Columbia</form:option>
                  <form:option value="DE">Delaware</form:option>
                  <form:option value="FL">Florida</form:option>
                  <form:option value="GA">Georgia</form:option>
                  <form:option value="HI">Hawaii</form:option>
                  <form:option value="ID">Idaho</form:option>
                  <form:option value="IL">Illinois</form:option>
                  <form:option value="IN">Indiana</form:option>
                  <form:option value="IA">Iowa</form:option>
                  <form:option value="KS">Kansas</form:option>
                  <form:option value="KY">Kentucky</form:option>
                  <form:option value="LA">Louisiana</form:option>
                  <form:option value="ME">Maine</form:option>
                  <form:option value="MD">Maryland</form:option>
                  <form:option value="MA">Massachusetts</form:option>
                  <form:option value="MI">Michigan</form:option>
                  <form:option value="MN">Minnesota</form:option>
                  <form:option value="MS">Mississippi</form:option>
                  <form:option value="MO">Missouri</form:option>
                  <form:option value="MT">Montana</form:option>
                  <form:option value="NE">Nebraska</form:option>
                  <form:option value="NV">Nevada</form:option>
                  <form:option value="NH">New Hampshire</form:option>
                  <form:option value="NJ">New Jersey</form:option>
                  <form:option value="NM">New Mexico</form:option>
                  <form:option value="NY">New York</form:option>
                  <form:option value="NC">North Carolina</form:option>
                  <form:option value="ND">North Dakota</form:option>
                  <form:option value="OH">Ohio</form:option>
                  <form:option value="OK">Oklahoma</form:option>
                  <form:option value="OR">Oregon</form:option>
                  <form:option value="PA">Pennsylvania</form:option>
                  <form:option value="RI">Rhode Island</form:option>
                  <form:option value="SC">South Carolina</form:option>
                  <form:option value="SD">South Dakota</form:option>
                  <form:option value="TN">Tennessee</form:option>
                  <form:option value="TX">Texas</form:option>
                  <form:option value="UT">Utah</form:option>
                  <form:option value="VT">Vermont</form:option>
                  <form:option value="VA">Virginia</form:option>
                  <form:option value="WA">Washington</form:option>
                  <form:option value="WV">West Virginia</form:option>
                  <form:option value="WI">Wisconsin</form:option>
                  <form:option value="WY">Wyoming</form:option>
                </form:select>
              </div>
              <div class="row">
                <form:label path="creditCard.zip" cssClass="required">Billing Zip Code</form:label>
                <form:input path="creditCard.zip" maxLength="5" cssClass="span-8 numOnly"
                  cssErrorClass="span-8 numOnly validationFailed" />
              </div>
            </div>
          </div>

          <div class="clear"></div>
          <!-- Buttons -->
          <div class="buttons">
            <a id="addCreditCardButton" href="#" class="button action-m"><span>Continue</span> </a> <input
              id="addCreditCardSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" />
          </div>

        </form:form>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/progress/activationProgress.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>