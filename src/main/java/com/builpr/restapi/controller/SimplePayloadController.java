package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.model.SimplePayload;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author Dominic Fuchs
 */
@RestController
public class SimplePayloadController {

    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = Constants.URL_SIMPLEPAYLOAD, method = RequestMethod.GET)
    public SimplePayload byParameter(
            @RequestParam(
                    value = "payload",
                    defaultValue = "Benutze den Parameter 'account' um das Payload-Attribut zu befüllen.",
                    required = false
            ) String payload
    ) {
        return new SimplePayload(payload);
    }

}