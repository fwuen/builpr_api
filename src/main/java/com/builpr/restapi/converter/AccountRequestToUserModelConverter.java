package com.builpr.restapi.converter;

import com.builpr.Constants;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.restapi.model.Request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * maps an account request to a speedment user model
 */
public class AccountRequestToUserModelConverter {
    public static User from(RegisterRequest registerRequest) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        return new UserImpl()
                .setUsername(registerRequest.getUsername())
                .setEmail(registerRequest.getEmail())
                .setPassword(BCrypt.hashpw(registerRequest.getPassword(), BCrypt.gensalt()))
                .setBirthday(Date.valueOf(registerRequest.getBirthday()))
                .setFirstname(registerRequest.getFirstname())
                .setLastname(registerRequest.getLastname())
                .setShowBirthday(registerRequest.isShowBirthday())
                .setShowEmail(registerRequest.isShowEmail())
                .setShowName(registerRequest.isShowName());
    }
}
