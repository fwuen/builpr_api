package com.builpr.restapi.controller;

import com.builpr.restapi.model.SimplePayload;
import com.speedment.Speedment;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimplePayloadController {

    @CrossOrigin(origins = "http://localhost:8081")
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