<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
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
        <input type="hidden" value="signup">
        <div class="header"><h1>Create Your Account</h1></div>
        <div class="spacer"></div>

        <div id="errorMessage" class="notVisible"></div>
        <c:if test="${message != null}">
            <div id="serverErrorMessage">${message}</div>
        </c:if>

        <div class="field_container">
            <label class="pad_top">Full Name:</label>
            <input type="text" id="fullName" name="fullName" placeholder="Enter Your Full Name">
            <span id="fullname_error" class="errorspan">*</span>
        </div>

        <div class="field_container">
            <label class="pad_top">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter a User Name">
            <span id="username_error" class="errorspan">*</span>
        </div>

        <div class="field_container">
            <label class="pad_top">Email:</label>
            <input type="email" id="email" name="email" placeholder="Enter Your Email Address">
            <span id="email_error" class="errorspan">*</span>
        </div>

        <div class="field_container">
            <label class="pad_top">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter a Password">
            <span id="password_error" class="errorspan">*</span>
        </div>

        <div class="field_container">
            <label class="pad_top">Confirm Password:</label>
            <input type="password" id="confirmpassword" name="confirmPassword" placeholder="Re-Enter Your Password">
            <span id="confirmpassword_error" class="errorspan">*</span>
        </div>

        <div class="field_container">
            <label class="pad_top">Date of Birth:</label>
            <input type="date" id="dateofbirth" name="birthdate">
            <span id="dateofbirth_error" class="errorspan">*</span>
        </div>
        
        <div class="field_container">
            <label class="pad_top">Security Question:</label>
            <select required onChange="toggleAnswer(this)" name="questionNo">
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
        <input type="hidden" name="action" value="signup">
        <input type="submit" class="submit" name="submit" value="Sign Up">
    </form>
    
    <footer>   
        <c:import url="/footer.jsp"/>
    </footer>
</body>
</html>
