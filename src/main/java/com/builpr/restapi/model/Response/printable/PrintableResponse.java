package com.builpr.restapi.model.Response.printable;

import lombok.Getter;
import lombok.Setter;

/**
 * Response for requesting a specific printable
 */
public class PrintableResponse extends PrintablePayload {

    /**
     * Flag if the printable is mine
     */
    @Getter
    @Setter
    boolean isMine;
}
