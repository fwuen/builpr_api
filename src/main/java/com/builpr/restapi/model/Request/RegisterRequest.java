package com.builpr.restapi.model.Request;

import lombok.Getter;

/**
 * registration request
 */
public class RegisterRequest {

    @Getter
    private String username;

    @Getter
    private String email;

    @Getter
    private String password;

    @Getter
    private String password2;

    @Getter
    private String birthday;

    @Getter
    private String firstname;

    @Getter
    private String lastname;

    @Getter
    private boolean showBirthday;

    @Getter
    private boolean showName;

    @Getter
    private boolean showEmail;

    
    public RegisterRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public RegisterRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterRequest setPassword2(String password2) {
        this.password2 = password2;
        return this;
    }

    public RegisterRequest setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public RegisterRequest setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public RegisterRequest setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public RegisterRequest setShowBirthday(boolean showBirthday) {
        this.showBirthday = showBirthday;
        return this;
    }

    public RegisterRequest setShowName(boolean showName) {
        this.showName = showName;
        return this;
    }

    public RegisterRequest setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
        return this;
    }
}
