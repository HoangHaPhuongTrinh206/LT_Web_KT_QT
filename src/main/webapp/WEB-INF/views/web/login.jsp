<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Đăng nhập</title>
</head>
<body>
  <div class="form-box">
    <h1>Đăng nhập</h1>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <c:if test="${not empty success}"><div class="alert alert-ok"><c:out value="${success}"/></div></c:if>
    <form method="post" action="${ctx}/login">
      <label>Tên đăng nhập</label>
      <input type="text" name="username" value="<c:out value='${username}'/>" required autofocus>
      <label>Mật khẩu</label>
      <input type="password" name="password" required>
      <button class="btn" type="submit">Đăng nhập</button>
    </form>
    <p class="muted">Chưa có tài khoản? <a href="${ctx}/register">Đăng ký</a></p>
  </div>
</body>
</html>
