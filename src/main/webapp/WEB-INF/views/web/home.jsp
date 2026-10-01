<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Trang chủ</title>
</head>
<body>
  <h1 class="page-title">Video theo danh mục</h1>

  <c:if test="${empty blocks}">
    <p class="muted">Chưa có danh mục nào (hãy chạy file database.sql hoặc thêm dữ liệu).</p>
  </c:if>

  <%-- Câu 4: mỗi category 1 khối, 3 video / trang. Câu 5: số trong ngoặc = số video của category --%>
  <c:forEach var="b" items="${blocks}">
    <section class="category-block">
      <h2 class="cat-title"><c:out value="${b.category.categoryname}"/> (${b.total})</h2>

      <c:choose>
        <c:when test="${empty b.page.items}">
          <p class="muted">Chưa có video trong danh mục này.</p>
        </c:when>
        <c:otherwise>
          <div class="grid">
            <c:forEach var="v" items="${b.page.items}">
              <%@ include file="/WEB-INF/views/common/card.jspf" %>
            </c:forEach>
          </div>
          <c:set var="pg" value="${b.page}"/>
          <c:set var="baseUrl" value="${ctx}/home?${b.otherParams}"/>
          <c:set var="pageParam" value="p${b.category.categoryId}"/>
          <%@ include file="/WEB-INF/views/common/pager.jspf" %>
        </c:otherwise>
      </c:choose>
    </section>
  </c:forEach>
</body>
</html>
