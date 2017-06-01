package com.builpr.restapi.Request.Printable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Request to create a new printable
 */
public class PrintableNewRequest {

    /**
     * user token
     */
    @Getter
    @Setter
    String token;

    /**
     * title of the printable
     */
    @Getter
    @Setter
    String title;

    /**
     * printable description
     */
    @Getter
    @Setter
    String description;


    /**
     * List of categories the user assigned to the printable
     */
    @Getter
    @Setter
    List<String> categories;
}
