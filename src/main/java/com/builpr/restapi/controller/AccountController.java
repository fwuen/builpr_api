package com.builpr.restapi.controller;

import com.builpr.database.db.builpr.user.User;
import com.builpr.restapi.converter.RegisterRequestToModelConverter;
import com.builpr.restapi.error.response.account.RegisterError;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.model.Response.Account.RegisterResponse;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * registration response
 */
@RestController
public class AccountController {

    private UserService userService;

    public AccountController() {
        userService = new UserService();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response<RegisterResponse, RegisterError> register(@RequestBody RegisterRequest registerRequest) throws Exception {

        Response<RegisterResponse, RegisterError> response = new Response<>();

        if (!registerRequest.getUsername().matches("[A-Za-z][A-Za-z0-9_]{5}[A-Za-z0-9]")) {
            response.setSuccess(false);
            response.addError(RegisterError.USERNAME_INVALID);
        }
        if (registerRequest.getPassword().length() < 7) {
            response.setSuccess(false);
            response.addError(RegisterError.PASSWORD_TOO_SHORT);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Date registerDate = simpleDateFormat.parse(registerRequest.getBirthday());
        Date currentDate = new Date(System.currentTimeMillis());

        if (registerDate.after(currentDate)) {
            response.setSuccess(false);
            response.addError(RegisterError.INVALID_DATE);
        }
        if (!registerRequest.getPassword().equals(registerRequest.getPassword2())) {
            response.setSuccess(false);
            response.addError(RegisterError.PASSWORDS_NOT_MATCHING);
        }
        if (userService.getByUsername(registerRequest.getUsername()) != null) {
            response.setSuccess(false);
            response.addError(RegisterError.USERNAME_TAKEN);
        }
        if (!EmailValidator.getInstance().isValid(registerRequest.getEmail())) {
            response.setSuccess(false);
            response.addError(RegisterError.INVALID_EMAIL);
        }
        if (!registerRequest.getFirstname().matches("[A-Za-z]{3,}")) {
            response.setSuccess(false);
            response.addError(RegisterError.INVALID_FIRSTNAME);
        }
        if(!registerRequest.getLastname().matches("[A-Za-z]{3,}")) {
            response.setSuccess(false);
            response.addError(RegisterError.INVALID_LASTNAME);
        }

        if (response.isSuccess()) {
            response.setPayload(new RegisterResponse());
        }

        User registeredUser = RegisterRequestToModelConverter.from(registerRequest);

        userService.persist(registeredUser);

        return response;
    }
}