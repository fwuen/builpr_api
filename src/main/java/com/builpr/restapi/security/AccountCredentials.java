package com.builpr.restapi.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Dominic Fuchs
 */
public class AccountCredentials {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

}
