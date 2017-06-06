package com.builpr.restapi.model.Request;

import lombok.Getter;
import lombok.Setter;

/**
 * registration request
 */
public class RegisterRequest {

    @Setter
    @Getter
    private String username;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private String password2;

    @Setter
    @Getter
    private String birthday;

    @Setter
    @Getter
    private String firstname;

    @Setter
    @Getter
    private String lastname;

    @Getter
    private boolean showBirthday;

    @Setter
    @Getter
    private boolean showName;

    @Setter
    @Getter
    private boolean showEmail;

    @Setter
    @Getter
    private String abc;

}
