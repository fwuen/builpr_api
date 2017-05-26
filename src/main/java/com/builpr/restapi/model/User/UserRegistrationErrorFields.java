package com.builpr.restapi.model.User;

/**
 * user model used in the registration process
 */
public class UserRegistrationErrorFields {

    private boolean emailTaken;

    private boolean usernameTaken;

    public UserRegistrationErrorFields() {
        emailTaken = false;
        usernameTaken = false;
    }

    public UserRegistrationErrorFields(boolean emailTaken, boolean usernameTaken) {
        this.emailTaken = emailTaken;
        this.usernameTaken = usernameTaken;
    }

    public void setEmailTaken(boolean emailTaken) {
        this.emailTaken = emailTaken;
    }

    public void setUsernameTaken(boolean usernameTaken) {
        this.usernameTaken = usernameTaken;
    }

    public boolean isEmailTaken() {
        return emailTaken;
    }

    public boolean isUsernameTaken() {
        return usernameTaken;
    }
}
