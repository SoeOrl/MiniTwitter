/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validateForm()
{
    validatePassword();
    validateName();
    
   return false;
}

function validatePassword()
{
        var errorDiv = document.getElementById("errorMessage")
	var password = document.getElementById("password");
	var confirmpassword = document.getElementById("confirmpassword");
        var error_password = document.getElementById("confirmpassword_error");	
        if(password.value != confirmpassword.value)
	{
		confirmpassword.style.backgroundColor = "yellow";
		error_password.style.display = "inline";
                errorDiv.value="Error! Password and Confirm Password do not Match"
                errorDiv.style.display="inline"
		return false;
	}
	else
	{
		error_password.style.display = "none";
		confirmpassword.style.backgroundColor = "white";
		return true;
	}
	
}

function validateName()
{       var errorDiv = document.getElementById("errorMessage")
	var name = document.getElementById("fullname");
	var nameError = document.getElementById("fullName_error");
	var regex = /[A-Za-z]+\s[A-Za-z]+/;
        if(!regex.test(name.value))
	{
		name.style.backgroundColor = "yellow";
		nameError.style.display = "inline";
                errorDiv.innerHTML="Error! Name is one word"
                errorDiv.style.display="inline"
		return false;
	}
	else
	{
		error_password.style.display = "none";
		confirmpassword.style.backgroundColor = "white";
		return true;
	}
	
}

function validateInvalidChar()
{       var errorDiv = document.getElementById("errorMessage")
        var formElements = document.forms["signup"].getElementsByTagName("input");
        for (var i = 0;i<formElements.length; i++)
        {
            //ADD CODE HERE
        }
	
}

function validatePasswordSecurity()
{       var errorDiv = document.getElementById("errorMessage")
	var name = document.getElementById("fullname");
	var nameError = document.getElementById("fullName_error");
	var regex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)$/;
       
       //ADD CODE HERE
	
}
