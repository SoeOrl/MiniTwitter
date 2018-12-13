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
        <c:if test="${message != null}">
            <div id="serverErrorMessage">${message}</div>
        </c:if>
        <c:choose>
            <c:when test="${(user == null) and (cookie.user == null)}">

            </c:when>
            <c:otherwise>
                <form action="membership" method="post" >
                    <input type="hidden" name="action" value="logout">
                    <input class="btn btn-primary" type="submit" id="signOut" value="Sign out"  />
                </form>
                <a href="home.jsp" class="btn btn-primary">Home</a>
                <a href="notifications.jsp" class="btn btn-primary">Notifications</a>
                <a href="profile.jsp" class="btn btn-primary">Profile</a>
                <div class="searchBarContainer">
                    <form class="searchForm" action="search" method="post" class="searchForm">
                        <input type="hidden" name="action" value="search">
                        <input class="searchBar" type="text" name="query" placeholder="Enter Your Search...">
                        <input class="searchButton" type="submit" value=""/>
                    </form>
                </div>            
            </c:otherwise>
        </c:choose>

    </body>
</html>
