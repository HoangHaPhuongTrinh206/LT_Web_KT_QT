<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><sitemesh:write property="title"/> | Admin</title>
  <link rel="stylesheet" href="${ctx}/assets/css/style.css">
  <sitemesh:write property="head"/>
</head>
<body class="role-admin">
  <%@ include file="/WEB-INF/decorators/_header.jspf" %>
  <div class="container admin-layout">
    <aside class="sidebar">
      <h3>Quản trị</h3>
      <a href="${ctx}/admin/home">Bảng điều khiển</a>
      <a href="${ctx}/admin/videos">Quản lý Video</a>
      <a href="${ctx}/admin/videos/add">+ Thêm video</a>
    </aside>
    <main class="content">
      <sitemesh:write property="body"/>
    </main>
  </div>
  <%@ include file="/WEB-INF/decorators/_footer.jspf" %>
</body>
</html>
