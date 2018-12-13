/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm()
{
    var errorDiv = document.getElementById("errorMessage");
    errorDiv.innerHTML = "";
    var bools = [];
    bools.push(validatePassword());
    if (bools.includes(false))
    {
        return false;
    }
    bools.push(validateName());
    if (bools.includes(false))
    {
        return false;
    }
    bools.push(validateInvalidChar());
    if (bools.includes(false))
    {
        return false;
    }
    bools.push(validatePasswordSecurity());
    if (bools.includes(false))
    {
        return false;
    }
        bools.push(checkAnswer());
    if (bools.includes(false))
    {
        return false;
    }
    
    errorDiv.innerHTML = "";
    errorDiv.style.display = "none";
    return true;
}

function validatePassword()
{
        var errorDiv = document.getElementById("errorMessage");
	var password = document.getElementById("password");
        var passwordError = document.getElementById("password_error");
	var confirmpassword = document.getElementById("confirmpassword");
        var error_password = document.getElementById("confirmpassword_error");	
        if(password.value !== confirmpassword.value)
	{
		confirmpassword.style.backgroundColor = "yellow";
                password.style.backgroundColor = "yellow";
		error_password.style.display = "inline";
                passwordError.style.display = "inline";
                errorDiv.innerHTML += "Error! Password and Confirm Password do not Match<br>";
                errorDiv.style.display="inline";
		return false;
	}
	else
	{
		confirmpassword.style.backgroundColor = "white";
                password.style.backgroundColor = "white";
		error_password.style.display = "none";
                passwordError.style.display = "none";
    
               
		return true;
	}
	
}

function validateName()
{       
        var errorDiv = document.getElementById("errorMessage");
        var name = document.getElementById("fullName");
	var nameError = document.getElementById("fullName_error");
	var regex = /[A-Za-z]+\s[A-Za-z]+.*/;
        if(!regex.test(name.value) && name.value != null)
	{
		name.style.backgroundColor = "yellow";
		nameError.style.display = "inline";
                errorDiv.innerHTML +="Error! Name is one word<br>";
                errorDiv.style.display="inline";
		return false;
	}
	else
	{
		nameError.style.display = "none";
		name.style.backgroundColor = "white";
                
                
		return true;
	}
	
}

function validateInvalidChar()
{       
        var errorDiv = document.getElementById("errorMessage");
        try {
        var formElements = document.forms["signup"].getElementsByTagName("input");
        }
        catch (err)
        {
            try {
                var formElements = document.forms["changeProfile"].getElementsByTagName("input");
            }
            catch(err)
            {
                console.log("Weirdness and stuff and things")
            }
        }
        var regex = /'+/;
        var bools = true;
        //dont iterate over the submit button
        for (var i = 0;i<formElements.length - 2; i++)
        {
            var formString = formElements[i].id +"_error";
            var formError = document.getElementById(formString);
            if (regex.test(formElements[i].value))
            {
                
                formElements[i].style.backgroundColor = "yellow";
                formError.style.display = "inline";
                if (bools)
                {
                errorDiv.innerHTML += "Error! Invalid Characters<br>";
                }
                errorDiv.style.display = "inline";
		bools = false;
            }
            else
            {
            formElements[i].style.backgroundColor = "white";
            formError.style.display = "none";
            }
        }
        return bools;
	
}

function validatePasswordSecurity()
{       
        var errorDiv = document.getElementById("errorMessage");
	var password = document.getElementById("password");
	var passwordError = document.getElementById("password_error");
	var regex = /[A-Z]/;
        var regex1 = /[a-z]/;
        var regex2 = /\d/;
        var regex3 = /.{8,}/;
       
        if(!(regex.test(password.value) && regex1.test(password.value) && regex2.test(password.value) && regex3.test(password.value))) 
	{
		password.style.backgroundColor = "yellow";
		passwordError.style.display = "inline";
                errorDiv.innerHTML += "Error! Password must contain at least: one uppercase, lowercase, and number. As well as being atleast 8 characters long<br>";
                errorDiv.style.display="inline";
		return false;
	}
	else
	{
		passwordError.style.display = "none";
		password.style.backgroundColor = "white";
            
                
		return true;
	}
	
}

function checkAnswer()
{
    var answerBox = document.getElementById("questionNo");
    var securityAnswer = document.getElementById("answer");
    var errorDiv = document.getElementById("errorMessage");
     if (answerBox.value == "0")
 {

     errorDiv.innerHTML += "Error! No question selected<br>";
                errorDiv.style.display="inline";
		return false;
 }
 else
 {
     return true;
 }
}
function toggleAnswer()
{
 var answerBox = document.getElementById("questionNo");
 var securityAnswer = document.getElementById("answer");
 if (answerBox.value != "0")
 {
     securityAnswer.style.display = "block";
 }
 else
 {
    securityAnswer.style.display = "none"; 
 }
}