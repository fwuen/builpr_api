package com.builpr.restapi.model.Request;

import lombok.Getter;

/**
 * @author Marco Geißler
 *
 * contains the request made, when editing a user
 */
public class ProfileEditRequest {
    
    @Getter
    private String email;

    @Getter
    private String oldPassword;

    @Getter
    private String password;

    @Getter
    private String password2;

    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    private String description;

    @Getter
    private Boolean showName;

    @Getter
    private Boolean showEmail;

    @Getter
    private Boolean showBirthday;

    public ProfileEditRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public ProfileEditRequest setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public ProfileEditRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public ProfileEditRequest setPassword2(String password2) {
        this.password2 = password2;
        return this;
    }

    public ProfileEditRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ProfileEditRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ProfileEditRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProfileEditRequest setShowName(boolean showName) {
        this.showName = showName;
        return this;
    }

    public ProfileEditRequest setShowEmail(boolean showEmail) {
        this.showEmail = showEmail;
        return this;
    }

    public ProfileEditRequest setShowBirthday(boolean showBirthday) {
        this.showBirthday = showBirthday;
        return this;
    }
}
