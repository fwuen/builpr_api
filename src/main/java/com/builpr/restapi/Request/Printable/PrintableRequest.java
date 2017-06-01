package com.builpr.restapi.Request.Printable;

import lombok.Getter;
import lombok.Setter;

/**
 * Request to get a specific printable
 */
public class PrintableRequest {

    /**
     * user token
     */
    @Getter
    @Setter
    String token;

    /**
     * id of the printable
     */
    @Getter
    @Setter
    int printableID;
}
