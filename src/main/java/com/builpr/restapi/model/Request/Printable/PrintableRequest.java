package com.builpr.restapi.model.Request.Printable;

import lombok.Getter;
import lombok.Setter;

/**
 * Request to get a specific printable
 */
public class PrintableRequest {

    /**
     * id of the printable
     */
    @Getter
    @Setter
    int printableID;
}
