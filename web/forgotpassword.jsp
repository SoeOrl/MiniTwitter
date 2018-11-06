<%-- 
    Document   : forgotpassword
    Created on : Oct 10, 2018, 10:42:03 AM
    Author     : Soeren
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <head>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <title>MiniTwitter</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
        <script src="includes/main.js"></script>
    </head>

    <body>
        <header>
            <c:import url="/header.jsp"/>
        </header>

        <form id="signup" action="membership" method="post" onsubmit="return validateForm();">
            <div class="header"><h1>Forgot Password</h1></div>
            <div class="spacer"></div>

            <div id="errorMessage" class="notVisible"></div>

            <div class="field_container">
                <label class="pad_top">Email:</label>
                <input type="email" id="email" name="email" placeholder="Enter your Email">
                <span id="email_error" class="errorspan">*</span>
            </div>

            <div class="field_container">
                <label class="pad_top">Security Question:</label>
                <select required onChange="toggleAnswer()" name="questionNo" id="questionNo">
                    <c:forEach items="${securityQuestions}" var="securityQuestion">
                        <option value="${securityQuestion.getQuestionNo()}">${securityQuestion.getQuestionText()}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="field_container" id="answer">
                <label class="pad_top">Answer:</label>
                <input type="text" id="securityAnswer" name="answer">
                <span id="securityAnswer_error" class="errorspan">*</span>
            </div>
            <input type="hidden" name="action" value="forgotPassword">
            <input type="submit" class="submit" name="submit" value="Sign Up">
        </form>

        <footer>   
            <c:import url="/footer.jsp"/>
        </footer>
    </body>
</html>
