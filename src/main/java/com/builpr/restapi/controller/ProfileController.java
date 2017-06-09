package com.builpr.restapi.controller;

import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.UserModelToProfileResponseConverter;
import com.builpr.restapi.error.exception.UserNotFoundException;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import org.springframework.web.bind.annotation.*;

/**
 * profile controller
 */

@RestController
public class ProfileController {

    private DatabaseUserManager userService;

    public ProfileController() {
        userService = new DatabaseUserManager();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
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
