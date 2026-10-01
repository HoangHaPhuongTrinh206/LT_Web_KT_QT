<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><sitemesh:write property="title"/> | VideoShare</title>
  <link rel="stylesheet" href="${ctx}/assets/css/style.css">
  <sitemesh:write property="head"/>
</head>
<body class="role-user">
  <%@ include file="/WEB-INF/decorators/_header.jspf" %>
  <main class="container content">
    <sitemesh:write property="body"/>
  </main>
  <%@ include file="/WEB-INF/decorators/_footer.jspf" %>
</body>
</html>
