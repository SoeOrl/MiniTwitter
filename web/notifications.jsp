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
    </head>
    <body>
        <header>
            <c:import url="/header.jsp"/>
        </header>


        <c:forEach items="${Notifications}" var="notification">
            <c:choose>
                <c:when test="${notification.twit != null}">
                    <div class="twitHomepage">
                        <img class="img-responsive rounded" id="twitImage" alt="" src="http://placehold.it/50x50">
                        <c:if test="${notification.twit.originUsername == user.username}">
                            <form action="homepage" method="post" class="deleteTwitForm">
                                <input type="hidden" name="action" value="deleteTwit">
                                <input type="hidden" name="twitToDelete" value="${notification.twit.twitId}">
                                <input class="btn btn-primary deleteTwitButton" type="submit" id="deleteTwit" value="Delete"  />
                            </form>
                        </c:if>
                        <div class="twitNames">
                            ${notification.twit.originFullname} @${notification.twit.originUsername}
                        </div>
                        ${twit.twit}
                    </div> 
                </c:when>
                <c:when test="${notification.follow != null}">
                    <div>
                        ${notification.follow.fullName}  <a class="taggable">@${notification.follow.username}</a> started following you on ${notification.postedDateTime}
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
