package com.builpr.restapi.controller;

import com.builpr.restapi.service.UserService;
import com.builpr.restapi.model.PublicUser;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public PublicUser showProfile(
            @RequestParam(
                    value = "username",
                    defaultValue = "0"
            ) String name
    ) throws Exception {

        return new PublicUser(userService.getByUsername(name));
    }
}
