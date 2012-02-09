<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/setupForms.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/addCoupon.js" />"></script>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/highlight/step/addPaymentMethod.js" />"></script>

</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient">
    <div class="container">Redeem Coupon</div>
  </div>

  <div class="container">
    <div id="main-content">
      <div class="span-18 colborder">

        <form:form id="addCoupon" cssClass="validatedForm" method="post" commandName="coupon"
          cssStyle="overflow:hidden; height:300px;">
          <!-- Error Alert -->
          <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.coupon'].allErrors}">
            <div class="row">
              <div class="alert error">
                <h1>Please correct the following problems</h1>
                <form:errors path="couponCode" />
                <form:errors path="startDate" />
                <form:errors path="endDate" />
                <form:errors path="quantity" />
                <spring:bind path="coupon">
                  <c:forEach items="${status.errorMessages}" var="error" varStatus="status">
                    <span id="global.${status.index}.errors"><c:out value="${error}" /> </span>
                  </c:forEach>
                </spring:bind>
              </div>
            </div>
          </c:if>

          <div class="slider" style="height: 140px;">
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Enter a Coupon Code</h3>
            <p>If you have a coupon code, enter the code below, select your device, and click "Apply". The coupon
              will be applied to your account automatically. If the coupon you have received is a discount on the
              monthly access fee, the discount will be applied when the monthly access fee is charged.</p>
            <div class="row">
              <form:label cssClass="required" path="couponCode">Coupon Code</form:label>
              <form:input cssClass="span-8" cssErrorClass="span-8 validationFailed" path="couponCode" />
            </div>
            <div class="row pushed">
              <span id="couponMessage"></span>
            </div>
            <div class="buttons">
              <a href="#" class="button action-m continue"><span>Next</span> </a>
            </div>
          </div>

          <div class="slider hidden" style="margin-left: 1000px; height: 140px;">
            <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Select the Device you want to apply the offer to</h3>


            <div class="row deviceList" style="margin-top: 30px; position: relative; height: 50px;">

              <div
                style="position: absolute; left: 0; top: 0; z-index: 20; height: 100%; width: 32px;
                background: no-repeat url('<spring:url value="/static/images/buttons/carousel-button-prev.png" />') 0px 10px;"></div>

              <img src="<spring:url value="/static/images/buttons/left_fade.png" />"
                class="screenshot-carousel-left-fade"
                style="height: 186px; visibility: visible; width: 50px; position: absolute; left: 0; top: 0; z-index: 10;">

              <div style="white-space: nowrap; position: absolute; bottom: 40%; overflow:hidden; height: 30px; width: 2200px;">
                <c:forEach var="accountDetail" items="${accountList}" varStatus="status">
                  <div class="device">
                    <a href="#" class="button semi-s" title="${accountDetail.encodedAccountNum}"><span>${accountDetail.deviceInfo.deviceLabel}</span>
                    </a>
                  </div>
                  <div class="device">
                    <a href="#" class="button semi-s" title="${accountDetail.encodedAccountNum}"><span>${accountDetail.deviceInfo.deviceLabel}</span>
                    </a>
                  </div>
                  <div class="device">
                    <a href="#" class="button semi-s" title="${accountDetail.encodedAccountNum}"><span>${accountDetail.deviceInfo.deviceLabel}</span>
                    </a>
                  </div>
                  <div class="device">
                    <a href="#" class="button semi-s" title="${accountDetail.encodedAccountNum}"><span>${accountDetail.deviceInfo.deviceLabel}</span>
                    </a>
                  </div>
                  <div class="device">
                    <a href="#" class="button semi-s" title="${accountDetail.encodedAccountNum}"><span>${accountDetail.deviceInfo.deviceLabel}</span>
                    </a>
                  </div>
                </c:forEach>
              </div>

              <img src="<spring:url value="/static/images/buttons/right_fade.png" />"
                class="screenshot-carousel-right-fade"
                style="height: 186px; visibility: visible; width: 50px; position: absolute; right: 0; top: 0; z-index: 10;">

              <div
                style="position: absolute; right: 0; top: 0; z-index: 20; height: 100%; width: 32px;
                background: no-repeat url('<spring:url value="/static/images/buttons/carousel-button-next.png" />') 0px 10px;"></div>

            </div>


            <input type="hidden" name="account" id="account" />
            <div class="buttons">
              <a id="addCouponButton" href="#" class="button action-m"><span>Apply</span> </a> <input
                id="addCouponSubmit" type="submit" name="_eventId_submit" value="Continue" class="hidden" /><a href="#"
                class="button action-m back" style="margin-right: 15px;"><span>Back</span> </a>
            </div>
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