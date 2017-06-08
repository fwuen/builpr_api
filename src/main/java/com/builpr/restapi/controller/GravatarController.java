package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.model.Response.GravatarResponse;
import com.builpr.restapi.utils.GravatarWrapper;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.web.bind.annotation.*;

@RestController
public class GravatarController {

    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = Constants.URL_GRAVATAR, method = RequestMethod.GET)
    public GravatarResponse byEmail(
            @RequestParam(value = "email") String email
    ) {
        if(EmailValidator.getInstance().isValid(email))
            return new GravatarResponse(new GravatarWrapper().getUrl(email));
        else
            return new GravatarResponse(Constants.USER_IMAGE_GRAVATAR_NOT_SET);
    }

}
