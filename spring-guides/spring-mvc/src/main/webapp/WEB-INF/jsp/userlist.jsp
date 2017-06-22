<%--
  Created by IntelliJ IDEA.
  User: luolibing
  Date: 2017/6/22
  Time: 下午9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>spring mvc</title>
</head>
<body>
<h2>spring mvc</h2>
<c:forEach items="${users}" var="user">
    <c:out value="${user.username}" /><br/>
    <c:out value="${user.age}" /><br/>
</c:forEach>
</body>
</html>
