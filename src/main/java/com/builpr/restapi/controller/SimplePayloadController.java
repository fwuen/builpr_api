package com.builpr.restapi.controller;

import com.builpr.restapi.model.SimplePayload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePayloadController {

    @RequestMapping(value = "/simplepayload", method = RequestMethod.GET)
    public SimplePayload byParameter(
            @RequestParam(
                    value = "payload",
                    defaultValue = "Benutze den Parameter 'payload' um das Payload-Attribut zu befüllen.",
                    required = false
            ) String payload
    ) {
        return new SimplePayload(payload);
    }

}