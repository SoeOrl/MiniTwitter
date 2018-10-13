<%-- 
    Document   : header.jsp
    Created on : Sep 24, 2015, 6:47:09 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:choose>
        <c:when test="${(user == null) and (cookie.user == null)}">
             NOTHING BECAUSE NOTHING SHOULD BE HERE
        </c:when>
        <c:otherwise>
             MAKE SIGNOUT BUTTON
        </c:otherwise>
             </c:choose>

    </body>
</html>
