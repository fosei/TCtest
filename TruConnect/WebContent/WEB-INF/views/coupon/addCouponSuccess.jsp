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

        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Coupon Applied!</h3>
        <p>The coupon was applied to your account. If the coupon you have received is a discount on the monthly
          access fee, the discount will be applied when the monthly access fee is charged.</p>
        <div>
          <b>${coupon.couponDetail.contract.description} for <c:choose>
              <c:when test="${coupon.couponDetail.contract.contractType > 0}">${coupon.couponDetail.duration} months</c:when>
              <c:when test="${coupon.couponDetail.contract.contractType < 0}">${coupon.couponDetail.amount} dollars</c:when>
              <c:otherwise>your account</c:otherwise>
            </c:choose> </b><br /> Device: ${accountDetail.deviceInfo.deviceLabel}
        </div>

        <div class="buttons">
          <a href="<spring:url value="/" />" class="button action-m"><span>Continue</span> </a>
        </div>
      </div>

      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>