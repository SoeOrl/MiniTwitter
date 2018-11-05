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
        <link rel="stylesheet" href="styles/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>       
        <title>JSP Page</title>
    </head>
    <body>
    <header>
        <c:import url="/header.jsp"/>
    </header>
        <c:if test="${(user == null) and (cookie.user == null)}">
            <c:redirect url="login.jsp"></c:redirect>
        </c:if>
            <div class="container-fluid">
            <div class="row rounded">
                <div class="col rounded border border-success" id="profileContainer">
                    <div class="topProfile"></div>
                    <div class="bottomProfile">
                            <a href="#"><img class="img-responsive rounded" id="profileImage" alt="" src="http://placehold.it/75x75"></a>
                            <div class="userNameandTag">
                                Username<br>
                                UserTag
                            </div>
                            <div class="userTweets">

                                TWEETS
                                Number of Tweets
                            </div>
                    </div>

                    <div class="panel panel-default panel-custom" id="Trending">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                Trends

                            </h3>
                        </div>
                    </div>
                </div>
                <div class="col">
                            <div class="composeTweet">
                                <div>
                                <form action="/action_page.php" id="composeTweet">
                                    <textarea cols="60" rows="5" maxlength="280" form="composeTweet"></textarea>
                                    <input type="submit" value="Tweet" id="tweetButton">
                                    <input type="hidden" name="action" value="tweet">
                                </form> 
                                </div>
                    </div>
                        <div class="showTweets rounded">
                            TWEETS HERE
                        </div>
                </div>

                <div class="col ">
                    <div class="whoToFollow rounded">
                                Who to follow
                    </div>
                    </div>

                </div>
            </div>
    <footer>   
        <c:import url="/footer.jsp"/>
    </footer>
    </body>
</html>
