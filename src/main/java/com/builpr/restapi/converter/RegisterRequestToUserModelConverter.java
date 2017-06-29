package com.builpr.restapi.converter;

import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.security.PasswordConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Date;

/**
 * maps an account request to a speedment user model
 */
public class RegisterRequestToUserModelConverter {
    public static User from(RegisterRequest registerRequest) {

        PasswordEncoder passwordEncoder = new PasswordConfiguration().passwordEncoder();

        return new UserImpl()
                .setUsername(registerRequest.getUsername())
                .setEmail(registerRequest.getEmail())
                .setPassword(passwordEncoder.encode(registerRequest.getPassword()))
                .setBirthday(Date.valueOf(registerRequest.getBirthday()))
                .setFirstname(registerRequest.getFirstname())
                .setLastname(registerRequest.getLastname())
                .setShowBirthday(registerRequest.isShowBirthday())
                .setShowEmail(registerRequest.isShowEmail())
                .setShowName(registerRequest.isShowName());
    }
}
