<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="v" value="${video}"/>
<%@ include file="/WEB-INF/views/common/poster.jspf" %>
<html>
<head>
  <title>${edit ? 'Sửa video' : 'Thêm video'}</title>
</head>
<body>
  <h1 class="page-title">${edit ? 'Sửa video' : 'Thêm video mới'}</h1>
  <c:if test="${not empty error}"><div class="alert alert-error"><c:out value="${error}"/></div></c:if>

  <form class="form-box wide" method="post" action="${ctx}/admin/videos/save" enctype="multipart/form-data">
    <input type="hidden" name="edit" value="${edit}">

    <label>Mã video</label>
    <c:choose>
      <c:when test="${edit}">
        <input type="text" name="videoId" value="<c:out value='${video.videoId}'/>" readonly>
      </c:when>
      <c:otherwise>
        <input type="text" name="videoId" maxlength="50" value="<c:out value='${video.videoId}'/>"
               placeholder="Để trống = tự sinh. Hoặc dán ID YouTube (11 ký tự) để phát video từ YouTube">
      </c:otherwise>
    </c:choose>

    <label>Tiêu đề *</label>
    <input type="text" name="title" maxlength="200" value="<c:out value='${video.title}'/>" required>

    <label>Category *</label>
    <select name="categoryId" required>
      <option value="">-- Chọn category --</option>
      <c:forEach var="cat" items="${categories}">
        <option value="${cat.categoryId}" ${video.category.categoryId == cat.categoryId ? 'selected' : ''}>
          <c:out value="${cat.categoryname}"/>
        </option>
      </c:forEach>
    </select>

    <label>Poster (chọn ảnh)</label>
    <c:if test="${not empty video.poster}">
      <img class="thumb big" src="${img}" alt="poster" onerror="this.onerror=null;this.src='${ctx}/assets/img/no-image.svg'">
    </c:if>
    <input type="file" name="posterFile" accept="image/*">
    <label>hoặc nhập link/tên ảnh (tối đa 50 ký tự)</label>
    <input type="text" name="posterUrl" maxlength="50" placeholder="vd: https://picsum.photos/seed/a1/300/200">

    <label>File video (.mp4 / .webm, tùy chọn)</label>
    <input type="file" name="videoFile" accept="video/mp4,video/webm">

    <label>View</label>
    <input type="number" name="views" min="0" value="${empty video.views ? 0 : video.views}">

    <label>Mô tả</label>
    <textarea name="description" rows="4" maxlength="500"><c:out value="${video.description}"/></textarea>

    <label class="check"><input type="checkbox" name="active" ${video.active ? 'checked' : ''}> Active (hiển thị cho User)</label>

    <div class="form-actions">
      <button class="btn" type="submit">Lưu</button>
      <a class="btn btn-light" href="${ctx}/admin/videos">Hủy</a>
    </div>
  </form>
</body>
</html>
