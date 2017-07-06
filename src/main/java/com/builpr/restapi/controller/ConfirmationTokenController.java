package com.builpr.restapi.controller;

import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseRegisterConfirmationTokenManager;
import com.builpr.database.service.DatabaseUserManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.builpr.Constants.SECURITY_CROSS_ORIGIN;
import static com.builpr.Constants.URL_REDEEM_CONFIRMATION_TOKEN;

/**
 * the controller that handles the account confirmation
 */
@RestController
public class ConfirmationTokenController {

    private DatabaseUserManager userManager;

    private DatabaseRegisterConfirmationTokenManager registerConfirmationTokenManager;

    public ConfirmationTokenController() {
        userManager = new DatabaseUserManager();
        registerConfirmationTokenManager = new DatabaseRegisterConfirmationTokenManager();
    }

    /**
     * endpoint that handles the incoming request when a user tries to redeem a confirmation token
     *
     * @param tokenAndID the token with the id of the user it belongs to appended at its end
     * @return a response with the status-code 200 if a user successfully redeemed his token or response with the status-
     * code 400 if the redemption wasn't successful
     */
    @CrossOrigin(SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_REDEEM_CONFIRMATION_TOKEN, method = RequestMethod.GET)
    public ResponseEntity<String> redeem(@RequestParam(value = "key") String tokenAndID) {

        // extract the token
        String token = tokenAndID.substring(0, 60);
        // get the userID from the token
        int userID = Integer.parseInt(tokenAndID.substring(60));

        if (registerConfirmationTokenManager.isPresent(userID, token)) {
            registerConfirmationTokenManager.delete(registerConfirmationTokenManager.getTokenEntry(userID, token));
            User updateUser = userManager.getByID(userID);
            updateUser.setActivated(true);
            userManager.update(updateUser);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

}
