<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Đăng ký</title>
</head>
<body>
  <div class="form-box">
    <h1>Đăng ký tài khoản</h1>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <form method="post" action="${ctx}/register">
      <label>Tên đăng nhập *</label>
      <input type="text" name="username" maxlength="50" value="<c:out value='${form.username}'/>" required>
      <label>Mật khẩu *</label>
      <input type="password" name="password" maxlength="50" required>
      <label>Nhập lại mật khẩu *</label>
      <input type="password" name="confirm" maxlength="50" required>
      <label>Họ tên *</label>
      <input type="text" name="fullname" maxlength="50" value="<c:out value='${form.fullname}'/>" required>
      <label>Email * (nhận mã OTP)</label>
      <input type="email" name="email" maxlength="150" value="<c:out value='${form.email}'/>" required>
      <label>Số điện thoại</label>
      <input type="text" name="phone" maxlength="15" value="<c:out value='${form.phone}'/>">
      <button class="btn" type="submit">Đăng ký &amp; nhận OTP</button>
    </form>
    <p class="muted">Đã có tài khoản? <a href="${ctx}/login">Đăng nhập</a></p>
  </div>
</body>
</html>
