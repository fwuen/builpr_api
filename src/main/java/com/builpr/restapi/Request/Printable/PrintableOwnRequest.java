package com.builpr.restapi.Request.Printable;


import lombok.Getter;
import lombok.Setter;

/**
 * Request to get the printables the user owns
 */
public class PrintableOwnRequest {

    /**
     * token of the user
     */
    @Getter
    @Setter
    String token;
}
