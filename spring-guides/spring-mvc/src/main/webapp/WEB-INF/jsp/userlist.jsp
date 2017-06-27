<%--
  Created by IntelliJ IDEA.
  User: luolibing
  Date: 2017/6/22
  Time: 下午9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
    <title>spring mvc</title>
    <link rel="stylesheet" href="http://localhost:8080/<spring:theme code='styleSheet'/>" type="text/css"/>
</head>
<body>
<h2><spring:message code="hello"/></h2>
Change theme: <a href="userlist.html?theme=sky">Theme 1 </a> | <a href="userlist.html?theme=yellow"> Theme 2 </a>
Change theme: <a href="userlist.html?locale=zh_CN">zh_CN </a> | <a href="userlist.html?locale=en_US"> en_US </a>
<c:forEach items="${users}" var="user">
    <c:out value="${user.username}" /><br/>
    <c:out value="${user.age}" /><br/>
</c:forEach>
</body>
</html>
