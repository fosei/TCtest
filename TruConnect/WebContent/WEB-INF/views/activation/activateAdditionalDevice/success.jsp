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
    <div class="container">Congratulations</div>
  </div>

  <div class="container">
    <div id="main-content">
      <!-- Begin Left Column -->
      <div class="span-18 colborder">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Your Device Has Been Activated!</h3>
        <p>
          You can now install or activate your TruConnect device as described in the user manual. If you have any
          questions, please visit our <a href="support.truconnect.com">support page</a> or click on your device below.
        </p>
        <p>
          <a class="button action-m" href="<spring:url value="/devices" />"><span>Continue</span> </a>
        </p>
        <p class="sub-navigation">
          <a href="http://www.truconnect.com/Knowledgebase/support/truconnect-usb-card/">TruConnect USB Card &raquo;</a><br /> <a
            href="http://www.truconnect.com/Knowledgebase/support/truconnect-mifi/">TruConnect MiFi &raquo;</a>
        </p>
      </div>
      <!-- Begin Right Column -->
      <div class="span-6 last sub-navigation formProgress">
        <%@ include file="/WEB-INF/includes/navigation/accountNav.jsp"%>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_links.jsp"%>
  </div>

</body>
</html>