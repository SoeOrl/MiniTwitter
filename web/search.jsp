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
        <div class="searchResultsContainer">
            <h1 class="searchResultsHeader">Search Results for: ${query}</h1>
            <div class="searchCategoryContainer rounded">
                <h2 class="searchCategoryHeader">Users</h2>
                <c:forEach items="${users}" var="user">
                    <div>
                        <div>
                            <c:out value="${user.getFullName()} @${user.getUsername()}"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="searchCategoryContainer rounded">
                <h2 class="searchCategoryHeader">Tweets</h2>
                <c:forEach items="${twits}" var="twit">
                    <div class="twitContainer">
                        <img class="img-responsive rounded" id="twitImage" alt="" src="http://placehold.it/50x50">
                        <div class="twitNames">
                            <c:out value="${twit.originFullname} @${twit.originUsername}"/>
                        </div>
                        <div class="twitText">
                            ${twit.twit}
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="searchCategoryContainer rounded">
                <h2 class="searchCategoryHeader">Hashtags</h2>
                <c:forEach items="${hashtags}" var="hashtag">
                    <div>
                        <div>
                            <c:out value="${hashtag.text}"/>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <footer>
            <c:import url="/footer.jsp"/>
        </footer>
    </body>
</html>