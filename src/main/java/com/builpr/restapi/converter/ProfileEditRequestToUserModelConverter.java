package com.builpr.restapi.converter;

import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.model.Request.ProfileEditRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * converts an edit-request to a user model with which the user can be updated
 */
public class ProfileEditRequestToUserModelConverter {

    public static User editUser(User editUser, ProfileEditRequest request) {

        if (request.getEmail() != null) {
            editUser.setEmail(request.getEmail());
        }
        // the correctness of the old and the new password is already checked in the controller
        if (request.getPassword() != null) {
            editUser.setPassword(request.getPassword());
        }
        if (request.getFirstName() != null) {
            editUser.setFirstname(request.getFirstName());
        }
        if (request.getLastName() != null) {
            editUser.setLastname(request.getLastName());
        }
        if (request.getDescription() != null) {
            editUser.setDescription(request.getDescription());
        }
        if (request.getShowName() != null) {
            editUser.setShowName(request.getShowName());
        }
        if (request.getShowBirthday() != null) {
            editUser.setShowName(request.getShowName());
        }
        if (request.getShowEmail() != null) {
            editUser.setShowEmail(request.getShowEmail());
        }

        return editUser;
    }

}
