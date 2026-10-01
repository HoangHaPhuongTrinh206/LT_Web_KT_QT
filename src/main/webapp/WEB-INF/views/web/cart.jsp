<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
  <title>Giỏ hàng</title>
</head>
<body>
  <h1 class="page-title">Giỏ hàng của bạn</h1>

  <c:if test="${not empty message}">
    <div class="alert"><c:out value="${message}"/></div>
  </c:if>

  <c:choose>
    <c:when test="${empty sessionScope.cart}">
      <p class="muted">Giỏ hàng trống.</p>
    </c:when>
    <c:otherwise>
      <table class="table">
        <thead>
          <tr>
            <th>Video</th>
            <th>Tên video</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <c:set var="totalSum" value="0"/>
          <c:forEach var="entry" items="${sessionScope.cart}">
            <c:set var="item" value="${entry.value}"/>
            <tr>
              <td>
                <img src="${item.poster}" alt="poster" width="100" onerror="this.onerror=null;this.src='${ctx}/assets/img/no-image.svg'">
              </td>
              <td><c:out value="${item.title}"/></td>
              <td><c:out value="${item.price}"/></td>
              <td>
                <form action="${ctx}/cart/update" method="post" style="display:inline;">
                  <input type="hidden" name="id" value="${item.videoId}"/>
                  <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.maxQuantity}" style="width: 60px;">
                  <button type="submit" class="btn btn-sm">Cập nhật</button>
                </form>
              </td>
              <td><c:out value="${item.totalPrice}"/></td>
              <td>
                <a href="${ctx}/cart/remove?id=${item.videoId}" class="btn btn-danger btn-sm">Xóa</a>
              </td>
            </tr>
            <c:set var="totalSum" value="${totalSum + item.totalPrice}"/>
          </c:forEach>
        </tbody>
        <tfoot>
          <tr>
            <th colspan="4" style="text-align: right;">Tổng cộng:</th>
            <th colspan="2"><c:out value="${totalSum}"/></th>
          </tr>
        </tfoot>
      </table>

      <div style="margin-top: 20px; text-align: right;">
        <form action="${ctx}/checkout" method="post">
          <input type="hidden" name="paymentMethod" value="COD"/>
          <button type="submit" class="btn btn-primary">Thanh toán (COD)</button>
        </form>
      </div>
    </c:otherwise>
  </c:choose>
</body>
</html>
