<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="tooltip" id="eventbox_${status.index}"
  style="position: absolute; border: 4px solid #cccccc; padding: 10px; display: none; background: white; width: 300px; height: 100px;">
  <h4>Event Detail - ${usageDetail.usageType}</h4>
  <c:choose>
    <c:when test="${usageDetail.usageType == 'Coupon' }">
      <span style='float: left;'>Start time: ${usageDetail.startTime.hour}:<fmt:formatNumber
          value="${usageDetail.startTime.minute}" pattern="00" /><br /> ${fn:replace(usageDetail.rate, 'TruConnect ',
        '' )} 
    </c:when>
    <c:when test="${usageDetail.usageType == 'Credit' }">
      Account credited for <span style='float: left;'>Amount: $<fmt:formatNumber
          value="${usageDetail.dollarAmount}" pattern="0.00" />
    </c:when>
    <c:when test="${usageDetail.usageType == 'Data'}">
      <div>
        <span style='float: left;'>Start time: ${usageDetail.startTime.hour}:<fmt:formatNumber
            value="${usageDetail.startTime.minute}" pattern="00" /> <c:choose>
            <c:when test="${usageDetail.startTime.hour >= 12}">pm</c:when>
            <c:otherwise>am</c:otherwise>
          </c:choose> </span> <span style='float: right;'>End time: ${usageDetail.dateAndTime.hour}:<fmt:formatNumber
            value="${usageDetail.endTime.minute}" pattern="00" /> <c:choose>
            <c:when test="${usageDetail.endTime.hour >= 12}">pm</c:when>
            <c:otherwise>am</c:otherwise>
          </c:choose> </span>
      </div>
      <div>
        <span style='float: left; clear: both;'>Usage: ${usageDetail.usageAmount} Mb</span>
      </div>
      <div>
        <span style='float: left;'>Amount: $<fmt:formatNumber value="${usageDetail.dollarAmount}" pattern="0.00" />
        </span> <span style='float: right;'>Rate: ${usageDetail.rate}</span>
      </div>
    </c:when>
    <c:when test="${usageDetail.usageType == 'Auto Top-Up'}">
      <span style='float: left;'>Top-Up Amount: $<fmt:formatNumber value="${usageDetail.dollarAmount}"
          pattern="0.00" /> </span>
      <c:set var="ccLength" value="${fn:length(usageDetail.notes)}" />
      <span style='float: left; clear: both;'>Payment Method: <c:out
          value="${fn:substring(usageDetail.notes, ccLength - 4, ccLength)}" /> </span>
    </c:when>
    <c:when test="${usageDetail.usageType == 'Access Fee'}">
      <span style='float: left;'>Period:
        ${usageDetail.startTime.month}/${usageDetail.startTime.day}/${usageDetail.startTime.year} -
        ${usageDetail.endTime.month}/${usageDetail.endTime.day}/${usageDetail.endTime.year}</span>
      <span style='float: left; clear: both;'>Amount: $<fmt:formatNumber value="${usageDetail.dollarAmount}"
          pattern="0.00" /> </span>
    </c:when>
    <c:when test="${usageDetail.usageType == 'Web Payment'}">
      <c:set var="ccLength" value="${fn:length(usageDetail.notes)}" />
      <span style='float: left;'>Payment Source: <c:out
          value="${fn:substring(usageDetail.notes, ccLength - 4, ccLength)}" /> </span>
    </c:when>
  </c:choose>
</div>