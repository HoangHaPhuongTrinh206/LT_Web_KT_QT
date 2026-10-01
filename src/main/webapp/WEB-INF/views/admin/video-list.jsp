<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Quản lý Video</title>
</head>
<body>
  <div class="toolbar">
    <h1 class="page-title">Quản lý Video (${pg.totalItems})</h1>
    <a class="btn" href="${ctx}/admin/videos/add">+ Thêm video</a>
  </div>

  <c:if test="${not empty flash}"><div class="alert alert-ok"><c:out value="${flash}"/></div></c:if>

  <div class="table-wrap">
  <table class="table">
    <thead>
      <tr><th>Poster</th><th>Mã video</th><th>Tiêu đề</th><th>Category</th><th>View</th><th>Share</th><th>Like</th><th>Active</th><th>Thao tác</th></tr>
    </thead>
    <tbody>
      <c:forEach var="v" items="${pg.items}">
        <%@ include file="/WEB-INF/views/common/poster.jspf" %>
        <tr>
          <td><img class="thumb" src="${img}" alt="" onerror="this.onerror=null;this.src='${ctx}/assets/img/no-image.svg'"></td>
          <td><c:out value="${v.videoId}"/></td>
          <td><a href="${ctx}/video?id=${v.videoId}" target="_blank"><c:out value="${v.title}"/></a></td>
          <td><c:out value="${v.category.categoryname}"/></td>
          <td>${v.views}</td>
          <td>${v.shareCount}</td>
          <td>${v.likeCount}</td>
          <td><c:out value="${v.active ? 'Có' : 'Không'}"/></td>
          <td class="actions">
            <a class="btn btn-sm" href="${ctx}/admin/videos/edit?id=${v.videoId}">Sửa</a>
            <form method="post" action="${ctx}/admin/videos/delete" style="display:inline"
                  onsubmit="return confirm('Xóa video ${v.videoId}? (các Share/Like liên quan cũng bị xóa)');">
              <input type="hidden" name="id" value="${v.videoId}">
              <input type="hidden" name="page" value="${pg.page}">
              <button class="btn btn-sm btn-danger" type="submit">Xóa</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty pg.items}"><tr><td colspan="9" class="muted">Chưa có video nào.</td></tr></c:if>
    </tbody>
  </table>
  </div>

  <c:set var="baseUrl" value="${ctx}/admin/videos?"/>
  <c:set var="pageParam" value="page"/>
  <%@ include file="/WEB-INF/views/common/pager.jspf" %>
</body>
</html>
