package com.builpr.restapi.controller;
import com.builpr.Constants;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.database.bridge.user.User;
import com.builpr.restapi.converter.AccountRequestToUserModelConverter;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.model.Response.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.builpr.Constants.*;
import static com.builpr.restapi.error.response.account.RegisterError.*;

/**
 * registration response
 */
@RestController
public class RegisterController {

    private DatabaseUserManager databaseUserManager;

    public RegisterController() {
        databaseUserManager = new DatabaseUserManager();
    }

    /**
     * registers a user
     *
     * @param registerRequest data given by the user
     * @return response that indicates if the registration was successful
     * @throws Exception
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_REGISTER, method = RequestMethod.POST)
    @ResponseBody
    public Response<String> register(@RequestBody RegisterRequest registerRequest) throws ParseException {

        Response<String> response = new Response<>();

        if (!registerRequest.getUsername().matches(USERNAME_PATTERN)) {
            response.setSuccess(false);
            response.addError(USERNAME_INVALID);
        }
        if (registerRequest.getPassword().length() < MIN_PASSWD_LENGTH) {
            response.setSuccess(false);
            response.addError(PASSWORD_TOO_SHORT);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        Date registerDate = simpleDateFormat.parse(registerRequest.getBirthday());

        Date currentDate = new Date(System.currentTimeMillis());

        if (registerDate == null || registerDate.after(currentDate) || !registerRequest.getBirthday().matches(DATE_PATTERN)) {
            response.setSuccess(false);
            response.addError(INVALID_DATE);
        }
        if (!registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            response.setSuccess(false);
            response.addError(PASSWORDS_NOT_MATCHING);
        }
        if (databaseUserManager.getByUsername(registerRequest.getUsername()) != null) {
            response.setSuccess(false);
            response.addError(USERNAME_TAKEN);
        }
        if (!EmailValidator.getInstance().isValid(registerRequest.getEmail())) {
            response.setSuccess(false);
            response.addError(INVALID_EMAIL);
        }
        if (databaseUserManager.getByEmail(registerRequest.getEmail()) != null) {
            response.setSuccess(false);
            response.addError(EMAIL_TAKEN);
        }
        if (registerRequest.getFirstname().isEmpty()) {
            response.setSuccess(false);
            response.addError(EMPTY_FIRSTNAME);
        }
        if (registerRequest.getLastname().isEmpty()) {
            response.setSuccess(false);
            response.addError(EMPTY_LASTNAME);
        }

        if (response.isSuccess()) {
            response.setPayload(null);
        }

        if (response.isSuccess()) {
            User registeredUser = AccountRequestToUserModelConverter.from(registerRequest);
            databaseUserManager.persist(registeredUser);
        }

        return response;
    }


}