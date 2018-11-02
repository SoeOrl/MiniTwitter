<%-- 
    Document   : test
    Created on : Oct 24, 2018, 5:28:18 PM
    Author     : Soeren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="styles/bootstrap.css" type="text/css"/>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <title>JSP Page</title>
    </head>
    <body class="twitter">
        <header>
            <a href="/home.jsp" class="btn btn-primary">Home</a>
            <a href="/notifications.jsp" class="btn btn-primary">Notifications</a>
            <a href="/signup.jsp" class="btn btn-primary">Profile</a>
				
        </header>
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
        </div>

    </body>
</html>
