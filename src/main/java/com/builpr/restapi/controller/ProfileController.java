package com.builpr.restapi.controller;

import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.ProfileEditRequestToUserModelConverter;
import com.builpr.restapi.converter.UserModelToProfileResponseConverter;
import com.builpr.restapi.error.exception.UserNotFoundException;
import com.builpr.restapi.model.Request.ProfileEditRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.builpr.Constants.*;
import static com.builpr.restapi.error.response.profile.ProfileEditError.*;

/**
 * @author Marco Geißler
 *
 * controller that handles the profile endpoints
 */

@RestController
public class ProfileController {

    private DatabaseUserManager databaseUserManager;

    public ProfileController() {
        databaseUserManager = new DatabaseUserManager();
    }

    /**
     * endpoint that handles the incoming request if a user tries to look at a profile
     *
     * @param id the id of the user the profile belongs to
     * @return the user's profile
     * @throws UserNotFoundException if a user to the given userID does not exist
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_PROFILE, method = RequestMethod.GET)
    @ResponseBody
    public Response<ProfilePayload> showProfile(
            @RequestParam(
                    value = "id",
                    defaultValue = "0",
                    required = false
            ) int id
    ) throws UserNotFoundException {
        User user = databaseUserManager.getByID(id);
        if (user == null) {
            throw new UserNotFoundException("user not found");
        }
        ProfilePayload profilePayload = UserModelToProfileResponseConverter.from(user);
        Response<ProfilePayload> response = new Response<>();
        response.setPayload(profilePayload);

        return response;
    }

    /**
     * endpoint that handles the incoming request if a user wants to edit his/her profile
     *
     * @param request   a request containing the attributes the user wants to change and the values which they should be changed to
     * @param principal representation of the user that is sending the request
     * @return a response containing information if the profile was successfully edited or, it that's not the case, a map of errors
     * that show the user why he couldn't edit his profile successfully
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_PROFILE_EDIT, method = RequestMethod.PUT)
    @ResponseBody
    public Response<String> editProfile(
            @RequestBody(required = false) ProfileEditRequest request,
            Principal principal
    )
    {
        String username = principal.getName();

        User user = databaseUserManager.getByUsername(username);

        Response<String> response = new Response<>();

        if (!EmailValidator.getInstance().isValid(request.getEmail()) && request.getEmail() != null) {
            response.addError(INVALID_EMAIL);
            response.setSuccess(false);
        }
        if ((!BCrypt.checkpw(request.getOldPassword(), user.getPassword()) && request.getOldPassword() != null) || request.getOldPassword() == null) {
            response.addError(OLD_PASSWORD_NOT_CORRECT);
            response.setSuccess(false);
        } else if (request.getOldPassword() != null) {
            if (request.getPassword().length() < MIN_PASSWD_LENGTH) {
                response.addError(NEW_PASSWORD_TOO_SHORT);
                response.setSuccess(false);
            } else {
                if (!request.getPassword().equals(request.getPassword2())) {
                    response.addError(NEW_PASSWORDS_NOT_MATCHING);
                    response.setSuccess(false);
                }
            }
        }
        if (request.getFirstName().equals("")) {
            response.addError(FIRSTNAME_EMPTY);
            response.setSuccess(false);
        }
        if (request.getLastName().equals("")) {
            response.addError(LASTNAME_EMPTY);
            response.setSuccess(false);
        }
        if (response.isSuccess()) {
            User editedUser = ProfileEditRequestToUserModelConverter.editUser(user, request);
            databaseUserManager.update(editedUser);
        }

        return response;
    }

    /**
     * endpoint that handles the incoming request if a user wants to delete his/her profile
     *
     * @param principal representation of the user that is sending the request and wants to delete his/her profile
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_PROFILE_DELETE, method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteProfile(Principal principal) {
        databaseUserManager.deleteByUsername(principal.getName());
    }
}
