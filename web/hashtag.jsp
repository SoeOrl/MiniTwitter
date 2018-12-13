<%-- 
    Document   : hashtag
    Created on : Dec 7, 2018, 4:55:25 PM
    Author     : jdodso227
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hashtags</title>

        <link rel="stylesheet" href="styles/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>

        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    </head>
    <body>
        <header>
            <c:import url="/header.jsp"/>
        </header>
        <h1>${hashtag}</h1>
        <div class="showTweets rounded">
            <c:forEach items="${hashtagTwits}" var="twit">
                <div class="twitContainer">
                    <img class="img-responsive rounded" id="twitImage" alt="" src="http://placehold.it/50x50">
                    <c:if test="${twit.originUsername == user.username}">
                        <form action="homepage" method="post" class="deleteTwitForm">
                            <input type="hidden" name="action" value="deleteTwit">
                            <input type="hidden" name="twitToDeleteId" value="${twit.uuid}">
                            <input type="hidden" name="twitToDeleteText" value="${fn:escapeXml(twit.twit)}">
                            <input class="btn btn-primary deleteTwitButton" type="submit" id="deleteTwit" value="Delete"  />
                        </form>
                    </c:if>
                    <div class="twitNames">
                        <c:out value="${twit.originFullname} @${twit.originUsername}"/>
                    </div>
                    <div class="twitText">
                        ${twit.twit}
                    </div>
                </div>
            </c:forEach>
        </div>
        <footer>
            <c:import url="/footer.jsp"/>
        </footer>
    </body>
</html>