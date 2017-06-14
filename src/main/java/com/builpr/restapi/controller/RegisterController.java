package com.builpr.restapi.controller;
import com.builpr.Constants;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenImpl;
import com.builpr.database.service.DatabaseRegisterConfirmationTokenManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.database.bridge.user.User;
import com.builpr.restapi.converter.AccountRequestToUserModelConverter;
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
    public Response<String> register(@RequestBody RegisterRequest registerRequest) throws ParseException, MessagingException {

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

        Date registerDate = simpleDateFormat.parse(registerRequest.getBirthday());

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

            User registeredUser = databaseUserManager.persist(AccountRequestToUserModelConverter.from(registerRequest));

            TokenGenerator tokenGenerator = new TokenGenerator(60, true);
            // send activation mail
            String confirmation_token = tokenGenerator.generate();
            String subject = "Your confirmation link";
            String mail = BASE_URL + URL_REDEEM_CONFIRMATION_TOKEN + "?key=" + confirmation_token + registeredUser.getUserId();

            new DatabaseRegisterConfirmationTokenManager().persist(new RegisterConfirmationTokenImpl().setUserId(registeredUser.getUserId()).setToken(confirmation_token));

            MailHelper.send(registeredUser.getEmail(), subject, mail);

        }

        return response;
    }


}