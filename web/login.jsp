<%-- 
    Document   : login.jsp
    Created on : Sep 24, 2015, 6:44:58 PM
    Author     : xl
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="styles/main.css" type="text/css"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body id="loginBody">
        <div class="loginBox">
            <h1>Login</h1>
            <form method="post" action="membership">
                <input type="hidden" name="action" value="login">
                <p>Username or Email</p>
                <input type="text" name="userIdent" placeholder="Enter Username or Email"></br>
                <p>Password</p>
                <input type="password" name="userPass" placeholder="Enter Password">
                <input type="submit" name="" value="Login">
                <input type="checkbox" name="rememberMe" value="rememberMe">Remember me</br>
                <a href="forgotpassword.jsp">Forgot Password?</a></br>
                <a href="signup.jsp">Sign Up</a></br>
            </form>
        </div>

<!--        <h1>
            <script type="text/javascript">
                response.redirect("/membership");
                </script>
            
        </h1>-->
    </body>
</html>
