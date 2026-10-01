<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Trang chủ Admin</title>
</head>
<body>
  <h1 class="page-title">Trang chủ Admin</h1>
  <p>Xin chào <b><c:out value="${sessionScope.currentUser.fullname}"/></b>, bạn đang đăng nhập với vai trò quản trị.</p>
  <div class="stats">
    <div class="stat"><span>${totalVideos}</span>Video</div>
    <div class="stat"><span>${totalCategories}</span>Category</div>
  </div>
  <p><a class="btn" href="${ctx}/admin/videos">Quản lý Video</a>
     <a class="btn btn-light" href="${ctx}/home">Xem trang User</a></p>
</body>
</html>
