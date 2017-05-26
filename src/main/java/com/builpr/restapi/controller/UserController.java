package com.builpr.restapi.controller;

import com.builpr.database.db.builpr.user.User;
import com.builpr.restapi.model.User.UserRegistration;
import com.builpr.restapi.model.User.UserRegistrationErrorFields;
import com.builpr.restapi.service.UserService;
import com.builpr.restapi.model.User.UserProfile;
import org.springframework.web.bind.annotation.*;
import com.builpr.restapi.exception.User.UserNotFoundException;

import static org.springframework.http.MediaType.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    /**
     * get a user by his/her username
     *
     * @param name the user's name
     * @return the found user
     * @throws UserNotFoundException when there is no user that matches the given username
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public UserProfile showProfile(
            @RequestParam(
                    value = "username",
                    defaultValue = "0"
            ) String name
    ) throws Exception {

        return new UserProfile(userService.getByUsername(name));
    }

    /**
     * create a new user
     *
     * @param user user to be saved
     * @return errorfields showing whether the given username or email-address is already used
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserRegistrationErrorFields createUser(
            @RequestBody(
                    required = false
            )UserRegistration user
            ) {

        User userToSave = user.toUser();
        return userService.saveUser(userToSave);
    }
}
