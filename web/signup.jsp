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
    <script src="main.js"></script>
</head>

<body>
    <header>
        <c:import url="/header.jsp"/>
    </header>
<form id="signup" action="membership" method="post" onsubmit="return validateForm();">
    <div class="header"><h1>Create Your Account</h1></div>
    <div class="spacer"></div>

    <div id="errorMessage" class="notVisible"></div>

    <div class="field_container">
        <label class="pad_top">Fullname:</label>
        <input type="text" id="fullname" placeholder="Enter Your Full Name">
        <span id="fullname_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Username:</label>
        <input type="text" id="username" placeholder="Enter a User Name">
        <span id="username_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Email:</label>
        <input type="email" id="email" placeholder="Enter Your Email Address">
        <span id="email_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Password:</label>
        <input type="password" id="password" placeholder="Enter a Password">
        <span id="password_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Confirm Password:</label>
        <input type="password" id="confirmpassword" placeholder="Re-Enter Your Password">
        <span id="confirmpassword_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Date of Birth:</label>
        <input type="date" id="dateofbirth">
        <span id="dateofbirth_error" class="errorspan">*</span>
    </div>

    <div class="field_container">
        <label class="pad_top">Security Question:</label>
        <select required onChange="toggleAnswer(this)">
            <option value="">None</option>
            <option value="pet">What was the name of you first pet?</option>
            <option value="car">What was the make of your first car?</option>
            <option value="school">What was the name of the first school you went to?</option>
        </select>
    </div>
    <div class="field_container" id="answer">
        <label class="pad_top">Answer:</label>
        <input type="text" id="securityAnswer">
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
