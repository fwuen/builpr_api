package com.builpr.restapi.controller;

import com.builpr.database.db.builpr.user.User;
import com.builpr.restapi.converter.UserModelToProfileResponseConverter;
import com.builpr.restapi.error.exception.UserNotFoundException;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.builpr.restapi.service.UserService;
import org.springframework.web.bind.annotation.*;

/**
 * profile controller
 */
public class ProfileController {

    private UserService userService;

    public ProfileController() {
        userService = new UserService();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public Response<ProfilePayload, String> showProfile(
            @RequestParam(
                    value = "id",
                    defaultValue = "0",
                    required = true
            ) int id
    ) throws UserNotFoundException {
        User user = userService.getByID(id);

        if (user == null) {
            throw new UserNotFoundException("user not found");
        }

        ProfilePayload profilePayload = UserModelToProfileResponseConverter.from(user);

        Response<ProfilePayload, String> response = new Response<>();

        response.setPayload(profilePayload);

        return response;
    }
}
