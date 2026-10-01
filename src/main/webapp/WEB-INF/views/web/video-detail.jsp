<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="v" value="${video}"/>
<%@ include file="/WEB-INF/views/common/poster.jspf" %>
<html>
<head>
  <title><c:out value="${video.title}"/></title>
</head>
<body>
  <%-- Câu 3: chi tiết 1 video --%>
  <div class="detail">
    <div class="player">
      <c:choose>
        <c:when test="${not empty videoFile}">
          <video class="detail-poster" controls preload="metadata" poster="${img}">
            <source src="${ctx}/uploads/${videoFile}">
            Trình duyệt không hỗ trợ phát video.
          </video>
        </c:when>
        <c:when test="${not empty youtubeId}">
          <iframe class="detail-poster" src="https://www.youtube.com/embed/${youtubeId}" allowfullscreen></iframe>
        </c:when>
        <c:otherwise>
          <img class="detail-poster" src="${img}" alt="poster"
               onerror="this.onerror=null;this.src='${ctx}/assets/img/no-image.svg'">
          <p class="muted small">Video này chưa có file để phát (admin có thể tải lên file .mp4 ở trang Sửa video).</p>
        </c:otherwise>
      </c:choose>
    </div>
    <div class="detail-info">
      <h1><b>Tiêu đề:</b> <c:out value="${video.title}"/></h1>
      <p><b>Mã video:</b> <c:out value="${video.videoId}"/></p>
      <p><b>Category name:</b> <c:out value="${video.category.categoryname}"/></p>
      <p><b>View:</b> ${video.views}</p>
      <p class="counts"><span>Share(${video.shareCount})</span><span>Like(${video.likeCount})</span></p>
      <p><b>Giá:</b> <c:out value="${video.price != null ? video.price : 0}"/> | <b>Còn lại:</b> <c:out value="${video.quantity != null ? video.quantity : 0}"/></p>
      <p class="desc"><c:out value="${video.description}"/></p>
      <p>
        <a class="btn btn-primary" href="${ctx}/cart/add?id=${video.videoId}">Thêm vào giỏ hàng</a>
      </p>
      <p><a class="btn btn-light" href="${ctx}/home">&laquo; Về trang chủ</a></p>
    </div>
  </div>
</body>
</html>
