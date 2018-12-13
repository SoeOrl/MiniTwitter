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
        <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>       
        <title>MiniTwitter</title>
    </head>
    <body>
        <header>
            <c:import url="/header.jsp"/>
        </header>
        <c:if test="${(user == null) and (cookie.user == null)}">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>
        <div class="container-fluid">
            <div class="row">
                <div class="col-2 rounded border border-success" id="profileContainer">
                    <div class="topProfile"></div>
                    <div class="bottomProfile">
                        <a href="#"><img class="img-responsive rounded" id="profileImage" alt="" src="http://placehold.it/75x75"></a>
                        <div class="userNameandTag">
                            <c:out value="${user.fullName}"/><br>
                            <c:out value="@${user.username}"/>
                        </div>
                        <div class="row">
                            <div class="col-4 userTweets">
                                TWEETS<br>
                                ${numTwits}
                            </div>
                            <div class="col-4 userTweets">
                                Following<br>
                                ${following}
                            </div>
                            <div class="col-4 userTweets">
                                Followed<br>
                                ${followed}
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default panel-custom" id="Trending">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                Trends
                                <c:forEach items="${trendingHashtags}" var="hashtag">
                                    <div class="trendingHashtag">
                                        <a href="hashtag?tag=${fn:replace(hashtag,"#","%23")}">${hashtag}</a>
                                    </div>
                                </c:forEach>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="col-5">
                    <div class="composeTweet">
                        <div>
                            <form action="homepage" id="composeTweet" method="post">
                                <textarea id="twitBody" name="twitBody" cols="60" rows="5" maxlength="280" form="composeTweet"></textarea>
                                <input type="submit" value="Tweet" id="tweetButton">
                                <input type="hidden" id="createtwit" name="action" value="createTwit">
                            </form> 
                        </div>
                    </div>
                    <div class="showTweets rounded">
                        <c:forEach items="${userTwits}" var="twit">
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
                </div>


                <div class="col-3 ">
                    Who to Follow
                    <div class="whoToFollow rounded">
                        <c:forEach items="${listToFollow}" var="followUsers">
                            <c:choose>
                                <c:when test="${followUsers.following == 0}">
                                    <div>
                                        <b><c:out value="${followUsers.fullName}"/></b> <a class="taggable"><c:out value="@${followUsers.username}"/></a> 
                                        <form action="homepage" method="post" class="test">
                                            <input type="hidden" name="action" value="follow">
                                            <input type="hidden" name="whoToFollow" value="${followUsers.username}">
                                            <input class="btn btn-primary followButton" type="submit" id="followButton" value="Follow">
                                        </form>
                                        <hr>
                                    </div>
                                </c:when>
                                <c:when test="${followUsers.following == 1}">
                                    <div>
                                        <b><c:out value="${followUsers.fullName}"/></b> <a class="taggable"><c:out value="@${followUsers.username}"/></a> 
                                        <form action="homepage" method="post" class="test">
                                            <input type="hidden" name="action" value="unFollow">
                                            <input type="hidden" name="whoToFollow" value="${followUsers.username}">
                                            <input class="btn btn-primary followButton" type="submit" id="followButton" value="UnFollow">
                                        </form>
                                        <hr>
                                    </div>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <footer>   
            <c:import url="/footer.jsp"/>
        </footer>
    </body>
</html>
