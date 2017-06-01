package com.builpr.restapi.Request.Printable;

import lombok.Getter;
import lombok.Setter;

/**
 * Delete-Request
 */
public class PrintableDeleteRequest {

    /**
     * token from the user
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

    /**
     * confirmation to delete the printable
     */
    @Getter
    @Setter
    boolean confirmation;

}
