package com.builpr.restapi.model.Request.Printable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Markus Goller
 *
 * Request to create a new printable
 */
public class PrintableNewRequest {

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

    /**
     * Printable-file stored in a byte-array
     */
    @Getter
    @Setter
    byte[] file;
}
