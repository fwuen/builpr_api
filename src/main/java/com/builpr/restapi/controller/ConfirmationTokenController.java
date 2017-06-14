package com.builpr.restapi.controller;

import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseRegisterConfirmationTokenManager;
import com.builpr.database.service.DatabaseUserManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.builpr.Constants.SECURITY_CROSS_ORIGIN;
import static com.builpr.Constants.URL_REDEEM_CONFIRMATION_TOKEN;

/**
 *
 */
@RestController
public class ConfirmationTokenController {

    private DatabaseUserManager userManager;

    public ConfirmationTokenController() {
        userManager = new DatabaseUserManager();
    }

    @CrossOrigin(SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_REDEEM_CONFIRMATION_TOKEN, method = RequestMethod.GET)
    public ResponseEntity<String> redeem(@RequestParam(value = "key") String tokenAndID) {

        String token = tokenAndID.substring(0, 60);
        int user_id = Integer.parseInt(tokenAndID.substring(60));

        if (new DatabaseRegisterConfirmationTokenManager().isPresent(user_id, token)) {
            User updateUser = userManager.getByID(user_id);

            updateUser.setActivated(true);

            userManager.update(updateUser);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
