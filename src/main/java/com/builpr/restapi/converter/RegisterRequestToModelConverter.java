package com.builpr.restapi.converter;

import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.restapi.model.Request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * maps a register request to a speedment user model
 */
public class RegisterRequestToModelConverter {
    public static User from(RegisterRequest registerRequest) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        return new UserImpl()
                .setUsername(registerRequest.getUsername())
                .setEmail(registerRequest.getEmail())
                .setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()))
                .setBirthday(Date.valueOf(registerRequest.getBirthday()))
                .setFirstname(registerRequest.getFirstname())
                .setLastname(registerRequest.getLastname());
    }
}
