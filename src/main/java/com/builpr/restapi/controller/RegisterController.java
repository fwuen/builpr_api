package com.builpr.restapi.controller;
import com.builpr.Constants;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenImpl;
import com.builpr.database.service.DatabaseRegisterConfirmationTokenManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.database.bridge.user.User;
import com.builpr.restapi.converter.RegisterRequestToUserModelConverter;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.utils.MailHelper;
import com.builpr.restapi.utils.TokenGenerator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.builpr.Constants.*;
import static com.builpr.restapi.error.response.account.RegisterError.*;

/**
 * @author Marco Geißler
 *
 * registration response
 */
@RestController
public class RegisterController {

    private DatabaseUserManager databaseUserManager;
    private DatabaseRegisterConfirmationTokenManager registerConfirmationTokenManager;

    public RegisterController() {
        databaseUserManager = new DatabaseUserManager();
        registerConfirmationTokenManager = new DatabaseRegisterConfirmationTokenManager();
    }

    /**
     * registers a user
     *
     * @param registerRequest request that contains all the necessary data for the registration
     * @return response containing information if the user was successfully created or, if that's not the case, an error map
     * that shows the user why he/she couldn't create the user
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_REGISTER, method = RequestMethod.POST)
    @ResponseBody
    public Response<String> register(@RequestBody RegisterRequest registerRequest){

        Response<String> response = new Response<>();

        if (!registerRequest.getUsername().matches(USERNAME_PATTERN)) {
            response.setSuccess(false);
            response.addError(USERNAME_INVALID);
        }
        if (registerRequest.getPassword().length() < MIN_PASSWD_LENGTH) {
            response.setSuccess(false);
            response.addError(PASSWORD_TOO_SHORT);
        } else {
            if (!registerRequest.getPassword().equals(registerRequest.getPassword2())) {
                response.setSuccess(false);
                response.addError(PASSWORDS_NOT_MATCHING);
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);

        Date registerDate = null;
        try {
            registerDate = simpleDateFormat.parse(registerRequest.getBirthday());
        } catch (ParseException e) {
            response.setSuccess(false);
            response.addError(INVALID_DATE);
        }

        Date currentDate = new Date(System.currentTimeMillis());

        if (registerDate == null || registerDate.after(currentDate) || !registerRequest.getBirthday().matches(DATE_PATTERN)) {
            response.setSuccess(false);
            response.addError(INVALID_DATE);
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

            User registeredUser = databaseUserManager.persist(RegisterRequestToUserModelConverter.from(registerRequest));

            TokenGenerator tokenGenerator = new TokenGenerator(60, true);
            // send activation mail
            String confirmation_token = tokenGenerator.generate();
            String subject = "Your confirmation link";
            String mail = BASE_URL + URL_REDEEM_CONFIRMATION_TOKEN + "?key=" + confirmation_token + registeredUser.getUserId();

            RegisterConfirmationToken registerConfirmationToken = new RegisterConfirmationTokenImpl().setUserId(registeredUser.getUserId()).setToken(confirmation_token);
            registerConfirmationTokenManager.persist(registerConfirmationToken);

            // if something doesn't work while trying to send the confirmation mail, delete the user and the token and add an error to the errorMap
            try {
                MailHelper.send(registeredUser.getEmail(), subject, mail);
            } catch (MessagingException e) {
                response.addError(CONFIRMATION_MAIL_SEND_ERROR);
                response.setSuccess(false);
                databaseUserManager.deleteByUsername(registeredUser.getUsername());
                registerConfirmationTokenManager.delete(registerConfirmationToken);
            }

        }

        return response;
    }


}