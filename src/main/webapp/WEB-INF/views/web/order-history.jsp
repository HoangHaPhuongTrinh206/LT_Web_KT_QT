<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Lịch sử đơn hàng</title>
</head>
<body>
  <h1 class="page-title">Lịch sử đơn hàng</h1>

  <div style="margin-bottom: 20px;">
    <form action="${ctx}/order-history" method="get">
      <label>Lọc theo trạng thái:</label>
      <select name="status">
        <option value="-1" ${selectedStatus == null || selectedStatus == -1 ? 'selected' : ''}>Tất cả</option>
        <option value="0" ${selectedStatus == 0 ? 'selected' : ''}>Mới</option>
        <option value="1" ${selectedStatus == 1 ? 'selected' : ''}>Đã xác nhận</option>
        <option value="2" ${selectedStatus == 2 ? 'selected' : ''}>Chuẩn bị hàng</option>
        <option value="3" ${selectedStatus == 3 ? 'selected' : ''}>Vận chuyển</option>
        <option value="4" ${selectedStatus == 4 ? 'selected' : ''}>Giao hàng</option>
        <option value="5" ${selectedStatus == 5 ? 'selected' : ''}>Đã giao</option>
        <option value="6" ${selectedStatus == 6 ? 'selected' : ''}>Hủy</option>
        <option value="7" ${selectedStatus == 7 ? 'selected' : ''}>Hoàn</option>
      </select>
      <button type="submit" class="btn">Lọc</button>
    </form>
  </div>

  <c:choose>
    <c:when test="${empty orders}">
      <p class="muted">Không có đơn hàng nào.</p>
    </c:when>
    <c:otherwise>
      <table class="table">
        <thead>
          <tr>
            <th>Mã ĐH</th>
            <th>Ngày đặt</th>
            <th>Tổng tiền</th>
            <th>Thanh toán</th>
            <th>Trạng thái</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="o" items="${orders}">
            <tr>
              <td><c:out value="${o.orderId}"/></td>
              <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
              <td><c:out value="${o.totalAmount}"/></td>
              <td><c:out value="${o.paymentMethod}"/></td>
              <td>
                <c:choose>
                  <c:when test="${o.status == 0}">Mới</c:when>
                  <c:when test="${o.status == 1}">Đã xác nhận</c:when>
                  <c:when test="${o.status == 2}">Chuẩn bị hàng</c:when>
                  <c:when test="${o.status == 3}">Vận chuyển</c:when>
                  <c:when test="${o.status == 4}">Giao hàng</c:when>
                  <c:when test="${o.status == 5}">Đã giao</c:when>
                  <c:when test="${o.status == 6}">Hủy</c:when>
                  <c:when test="${o.status == 7}">Hoàn</c:when>
                  <c:otherwise>Không xác định</c:otherwise>
                </c:choose>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>
