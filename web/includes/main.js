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
	var name = document.getElementById("fullname");
	var nameError = document.getElementById("fullname_error");
	var regex = /[A-Za-z]+\s[A-Za-z]+.*/;
        if(!regex.test(name.value))
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
        var formElements = document.forms["signup"].getElementsByTagName("input");
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
	var regex = /^(?=.*\d)(?=.*[A-Z])(?=.*[a-z]).+$/;
       
        if(!regex.test(password.value))
	{
		password.style.backgroundColor = "yellow";
		passwordError.style.display = "inline";
                errorDiv.innerHTML += "Error! Password must contain at least: one uppercase, lowercase, and number<br>";
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

function toggleAnswer(selectedQuestion)
{
 var answerBox = document.getElementById("answer");
 if (selectedQuestion.value !== "None")
 {
     answerBox.style.display = "block";
 }
 else
 {
    answerBox.style.display = "none"; 
 }
}