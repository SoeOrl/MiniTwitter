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
            <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body id="loginBody">
    <header>
        <c:import url="/header.jsp"/>
    </header>
        <div class="loginBox">
            <h1>Login</h1>
            <form method="post" action="membership">
                <input type="hidden" name="action" value="login">
                <p>Username or Email</p>
                <input type="text" name="userIdent" placeholder="Enter Username or Email"></br>
                <p>Password</p>
                <input type="password" name="userPass" placeholder="Enter Password">
                <input type="submit" name="" value="Login">
                <input type="checkbox" name="remeberMe" value="remeberMe">Remember me</br>
                <a href="forgotpassword.jsp">Forgot Password?</a></br>
                <a href="signup.jsp">Sign Up</a></br>
            </form>
        </div>
    <footer>   
        <c:import url="/footer.jsp"/>
    </footer>
    </body>
</html>
