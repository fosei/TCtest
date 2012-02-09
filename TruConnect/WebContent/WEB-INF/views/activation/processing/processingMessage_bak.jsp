<%@ include file="/WEB-INF/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/includes/doctype.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>TruConnect Account Management</title>
<%@ include file="/WEB-INF/includes/headTags.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/javascript/pages/activation_autoSubmit.js" />"></script>
</head>
<body>
  <%@ include file="/WEB-INF/includes/popups.jsp"%>
  <%@ include file="/WEB-INF/includes/header.jsp"%>

  <div class="blueTruConnectGradient"></div>

  <div class="container">
    <div id="main-content">

      <div class="span-18">
        <h3 style="margin-bottom: 10px; padding-bottom: 0px;">Building your TruConnect services... this may take a
          few minutes</h3>
        <form:form id="processingActivation" method="post" commandName="activationMessage">
          <div style="position: relative;">
            <img src="<spring:url value="/static/images/util/ajax_working.gif" />" style="margin-right: 30px;" />
            <form:input path="message"
              cssStyle="line-height:16px; font-size:16px; border: none; position:absolute; bottom:40%; width:100%;"
              title="${activationMessage.message}" />
          </div>
          <div class="buttons hidden">
            <input id="processingActivationSubmit" type="submit" name="_eventId_autoSubmit" />
          </div>
        </form:form>
      </div>

    </div>
    <%@ include file="/WEB-INF/includes/footer_nolinks.jsp"%>
  </div>

</body>
</html>