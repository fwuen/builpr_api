package com.builpr.restapi.converter;

import com.builpr.Constants;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.security.PasswordConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * maps an account request to a speedment user model
 */
public class AccountRequestToUserModelConverter {
    public static User from(RegisterRequest registerRequest) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        PasswordEncoder passwordEncoder = new PasswordConfiguration().passwordEncoder();

        return new UserImpl()
                .setUsername(registerRequest.getUsername())
                .setEmail(registerRequest.getEmail())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .setBirthday(Date.valueOf(registerRequest.getBirthday()))
                .setFirstname(registerRequest.getFirstname())
                .setLastname(registerRequest.getLastname())
                .setShowBirthday(registerRequest.isShowBirthday() ? 1 : 0)
                .setShowEmail(registerRequest.isShowEmail() ? 1 : 0)
                .setShowName(registerRequest.isShowName() ? 1 : 0);
    }
}
