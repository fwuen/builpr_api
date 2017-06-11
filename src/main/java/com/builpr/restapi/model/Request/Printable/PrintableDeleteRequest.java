package com.builpr.restapi.model.Request.Printable;

import lombok.Getter;
import lombok.Setter;

/**
 * Delete-Request
 */
public class PrintableDeleteRequest {

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
