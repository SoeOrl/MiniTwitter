<%-- 
    Document   : notifications
    Created on : Nov 5, 2018, 3:46:35 PM
    Author     : Soeren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>MiniTwitter</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <link rel="stylesheet" href="styles/bootstrap.css" type="text/css"/>
        <%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
    </head>
    <body>
        <header>
            <c:import url="/header.jsp"/>
        </header>


        <c:forEach items="${Notifications}" var="notification">
            <c:choose>
                <c:when test="${notification.twit != null}">
                    <div class="twitHomepage">

                        <div class="twitContainer">
                        <img class="img-responsive rounded" id="twitImage" alt="" src="http://placehold.it/50x50">
                        <div class="twitNames">
                                    <c:out value="${fn:escapeXml(notification.twit.originFullname)} @${fn:escapeXml(notification.twit.originUsername)}"/>
                                </div>
                                    ${notification.twit.twit}
                    </div>
                    </div> 
                </c:when>
                <c:when test="${notification.follow != null}">
                    <div>
                         <c:out value="${notification.follow.fullName}"/>  <a class="taggable"> <c:out value="@${notification.follow.username}"/> </a> started following you on  <c:out value="${notification.postedDateTime}"/>
                    </div>
                </c:when>
            </c:choose>

        </c:forEach>
        <c:if test="${empty Notifications}">
            You have no new Notifications
        </c:if>

        <footer>
            <c:import url="/footer.jsp"/>
        </footer>
    </body>
</html>
