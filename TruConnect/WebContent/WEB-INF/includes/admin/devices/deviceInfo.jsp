<div class="admin_tooltip tooltip hidden">
  <div>
  <li class="header">Device Information</li>
  <li>Account Number: ${device.deviceInfo.accountNo}</li>
  <li>Device ID: ${device.deviceInfo.deviceId}</li>
  <li>Status: ${device.deviceInfo.deviceStatus}</li>
  <li>Status ID: ${device.deviceInfo.deviceStatusId}</li>
  </div>
  <div>
    <c:forEach var="package" items="${device.account.packageList}">
      <li class="header">Package Information</li>
      <li>Package ID: ${package.packageid}</li>
      <li>Package Name: ${package.packageName}</li>
      <c:if test="${!empty package.componentlist}">
        <li class="header">Component Information</li>
        <c:forEach var="component" items="${package.componentlist}">
          <li>Component ID: ${component.componentId}</li>
          <li>Component Name: ${component.componentName}</li>
        </c:forEach>
      </c:if>
    </c:forEach>
  </div>
  <div>
  <li class="header">Service Information</li>
  <c:forEach var="service" items="${device.account.serviceinstancelist}">
    <li>Subscriber Number: ${service.subscrno}</li>
    <li>External ID: ${service.externalid}</li>
    <li>External ID Type: ${service.externalidtype}</li>
    <li>Active Date: ${service.activedate}</li>
    <li>Inactive Date: ${service.inactivedate}</li>
  </c:forEach>
  </div>
</div>