<%-- 
    Document   : home.jsp
    Created on : Sep 24, 2015, 6:47:02 PM
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
    <header>
        <c:import url="/header.jsp"/>
    </header>
        <c:if test="${(user == null) and (cookie.user == null)}">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>
            Welcome to your homepage ${cookie.user}
            ${user}
    <footer>   
        <c:import url="/footer.jsp"/>
    </footer>
    </body>
</html>
