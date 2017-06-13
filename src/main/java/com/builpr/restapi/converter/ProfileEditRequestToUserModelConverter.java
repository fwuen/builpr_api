package com.builpr.restapi.converter;

import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.model.Request.ProfileEditRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * converts an edit-request to a user model with which the user can be updated
 */
public class ProfileEditRequestToUserModelConverter {

    public static User from(ProfileEditRequest request) {
        return new UserImpl()
                .setEmail(request.getEmail())
                .setPassword(new BCryptPasswordEncoder().encode(request.getPassword()))
                .setFirstname(request.getFirstName())
                .setLastname(request.getLastName())
                .setDescription(request.getDescription())
                .setShowEmail(request.isShowName())
                .setShowEmail(request.isShowEmail())
                .setShowBirthday(request.isShowBirthday());
    }

}
