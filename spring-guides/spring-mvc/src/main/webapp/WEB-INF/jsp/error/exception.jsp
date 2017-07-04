<%--
  Created by IntelliJ IDEA.
  User: luolibing
  Date: 2017/7/4
  Time: 上午7:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
    <title>exception</title>
</head>
<body>
    exception: <c:out value="${requestScope.exception}" />
    exceptionStack: <c:out value="${requestScope.exceptionStack}" />
</body>
</html>
