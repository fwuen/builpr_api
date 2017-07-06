package com.builpr.restapi.error.response.profile;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Marco Geißler
 *
 * errors that can occur when editing a profile
 */
public enum ProfileEditError implements MappableError {
    INVALID_EMAIL(1, "the given e-mail-address is invalid"),
    OLD_PASSWORD_NOT_CORRECT(2, "the given password is not correct"),
    NEW_PASSWORDS_NOT_MATCHING(3, "the new passwords are not matching"),
    NEW_PASSWORD_TOO_SHORT(4, "the new password is too short"),
    FIRSTNAME_EMPTY(5, "the firstname cannot be empty"),
    LASTNAME_EMPTY(6, "the lastname cannot be empty");

    private final int CODE;
    private final String DESCRIPTION;

    private ProfileEditError(int code, String description) {
        CODE = code;
        DESCRIPTION = description;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public int getCode() {
        return CODE;
    }

    @Override
    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(CODE, DESCRIPTION);

        return map;
    }
}
