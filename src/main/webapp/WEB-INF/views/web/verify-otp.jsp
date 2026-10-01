<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Xác thực OTP</title>
</head>
<body>
  <div class="form-box">
    <h1>Kích hoạt tài khoản bằng OTP</h1>
    <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>
    <c:if test="${not empty success}"><div class="alert alert-ok"><c:out value="${success}"/></div></c:if>
    <c:if test="${not empty sessionScope.demoOtp}">
      <div class="alert alert-warn">Chế độ demo &ndash; mã OTP của bạn là: <b>${sessionScope.demoOtp}</b></div>
    </c:if>
    <p class="muted">Nhập mã gồm 6 chữ số đã gửi tới <b><c:out value="${sessionScope.otpEmail}"/></b></p>
    <form method="post" action="${ctx}/verify-otp">
      <label>Mã OTP</label>
      <input type="text" name="otp" maxlength="6" pattern="\d{6}" inputmode="numeric" required autofocus>
      <button class="btn" type="submit">Xác nhận</button>
    </form>
    <form method="post" action="${ctx}/verify-otp">
      <input type="hidden" name="action" value="resend">
      <button class="btn btn-light" type="submit">Gửi lại OTP</button>
    </form>
  </div>
</body>
</html>
