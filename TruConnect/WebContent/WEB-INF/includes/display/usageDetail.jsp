<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<tr>
  <td>
    ${usageDetail.dateAndTime.month}/${usageDetail.dateAndTime.day}/${usageDetail.dateAndTime.year}
    ${usageDetail.dateAndTime.hour}:<fmt:formatNumber value="${usageDetail.dateAndTime.minute}" pattern="00" />
    <c:choose>
      <c:when test="${usageDetail.dateAndTime.hour >= 12}">pm</c:when>
      <c:otherwise>am</c:otherwise>
    </c:choose>
  </td>
  <td>
    ${usageDetail.usageType}
  </td>
  <td style="text-align: right;">
    <c:choose>
      <c:when test="${usageDetail.usageType == 'Coupon'}">
        ${fn:replace(usageDetail.rate, 'TruConnect ', '' )}
      </c:when>
      <c:otherwise>
        ${usageDetail.usageAmount} <c:if test="${usageDetail.usageAmount > 0.0}">Mb</c:if>
      </c:otherwise>
    </c:choose>
  </td>
  <td style="text-align: right;">
    <c:choose>
      <c:when test="${usageDetail.usageType == 'Access Fee' && usageDetail.discount > 0.0}">
        <c:set var="fullAmount" value="${usageDetail.dollarAmount - usageDetail.discount}" />
        <c:choose>
          <c:when test="${fullAmount != -4.99}">
            $<fmt:formatNumber value="${usageDetail.dollarAmount}" pattern="0.000" />
          </c:when>
          <c:otherwise>
            $<fmt:formatNumber value="${fullAMount}" pattern="0.000" />
          </c:otherwise>
        </c:choose>
        + $<fmt:formatNumber value="${usageDetail.discount}" pattern="0.000" />
      </c:when>
      <c:otherwise>
        $<fmt:formatNumber value="${usageDetail.dollarAmount}" pattern="0.000" />
      </c:otherwise>
    </c:choose>
  </td>
  <td style="text-align: right;">
    $<fmt:formatNumber value="${usageDetail.balance}" pattern="0.00" />
  </td>
  <td>
    <img class="info" src="<spring:url value="/static/images/buttons/i.png" />" />
    <%@ include file="/WEB-INF/includes/display/usageDetailEventBox.jsp"%>
  </td>
</tr>