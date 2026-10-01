<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Sản phẩm</title>
</head>
<body>
  <h1 class="page-title">Tất cả video (${pg.totalItems})</h1>
  <c:if test="${empty pg.items}"><p class="muted">Chưa có video nào.</p></c:if>
  <div class="grid">
    <c:forEach var="v" items="${pg.items}">
      <%@ include file="/WEB-INF/views/common/card.jspf" %>
    </c:forEach>
  </div>
  <c:set var="baseUrl" value="${ctx}/products?"/>
  <c:set var="pageParam" value="page"/>
  <%@ include file="/WEB-INF/views/common/pager.jspf" %>
</body>
</html>
