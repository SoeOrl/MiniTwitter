package business;

import java.io.IOException;

import static dataaccess.UserUtil.searchByEmail;
import static dataaccess.UserUtil.searchByUsername;

/**
 * Validates if a user's fields are all valid
 *
 * @author jdodso227
 */
public class UserValidator {

    private User user;
    private String confirmPassword;
    public String errorMessage;

    public UserValidator(User user, String confirmPassword) {
        this.user = user;
        this.confirmPassword = confirmPassword;
        errorMessage = "";
    }

    private boolean fullNameIsValid() {
        if (!empty(user.getFullName())) {
            return true;
        } else {
            errorMessage = "Full Name cannot be empty";
            return false;
        }
    }

    private boolean emailIsValid() {
        try {
            if (empty(user.getEmail())) {
                errorMessage = "Email cannot be empty";
                return false;
            } else if (searchByEmail(user.getEmail()) != null) {
                errorMessage = "Email already exists";
                return false;
            } else {
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            errorMessage = "Server error - could not validate email";
            return false;
        }
    }

    private boolean userNameIsValid() {
        try {
            if (empty(user.getUsername())) {
                errorMessage = "Username cannot be empty";
                return false;
            } else if (searchByUsername(user.getUsername()) != null) {
                errorMessage = "Username already exists";
                return false;
            } else {
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            errorMessage = "Server error - Could not validate username";
            return false;
        }
    }

    private boolean passwordIsValid() {
        if (empty(user.getPassword())) {
            errorMessage = "Password cannot be empty";
            return false;
        } else if (empty(confirmPassword)) {
            errorMessage = "Confirm Password cannot be empty";
            return false;
        } else if (!user.getPassword().equals(confirmPassword)) {
            errorMessage = "Passwords don't match";
            return false;
        } else {
            return true;
        }
    }

    private boolean birthdateIsValid() {
        if (empty(user.getBirthdate().toString())) {
            errorMessage = "Birthdate cannot be empty";
            return false;
        } else {
            return true;
        }
    }

    private boolean answerIsValid() {
        if (empty(user.getAnswer())) {
            errorMessage = "Answer cannot be empty";
            return false;
        } else {
            return true;
        }
    }

    private boolean empty(String value) {
        return value == null || value.isEmpty();
    }

    private boolean noInvalidChars() {
        if (user.toString().contains("`")) {
            errorMessage = "Input contains invalid character";
            return false;
        } else {
            return true;
        }
    }

    public boolean isValid() {
        return noInvalidChars() && fullNameIsValid() && userNameIsValid() && emailIsValid()
                && passwordIsValid() && birthdateIsValid() && answerIsValid();
    }
}
