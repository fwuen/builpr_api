package com.builpr.restapi.error.response.account;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Marco Geißler
 *
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
    EMPTY_LASTNAME(9, "The given last name is empty"),
    CONFIRMATION_MAIL_SEND_ERROR(10, "There was an error sending the confirmation-mail");


    private final int CODE;
    private final String DESCRIPTION;

    private RegisterError(int code, String description) {
        this.CODE = code;
        this.DESCRIPTION = description;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public int getCode() {
        return CODE;
    }

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(CODE, DESCRIPTION);

        return map;
    }
}
