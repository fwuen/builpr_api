package com.builpr.restapi.error.response.account;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * enum containing all the possible registration errors
 */
public enum RegisterError implements MappableError {

    USERNAME_INVALID(1, "The given username is invalid"),
    USERNAME_TAKEN(2, "The given username is already taken"),
    EMAIL_TAKEN(3, "There is already an account using this email-address"),
    PASSWORDS_NOT_MATCHING(4, "The passwords are not matching"),
    PASSWORD_TOO_SHORT(5, "The password is too short"),
    INVALID_DATE(6, "The given date is not valid"),
    INVALID_EMAIL(7, "The given email-address is invalid"),
    EMPTY_FIRSTNAME(8, "The given fist name is empty"),
    EMPTY_LASTNAME(9, "The given last name is empty");


    private final int code;
    private final String description;

    private RegisterError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}
