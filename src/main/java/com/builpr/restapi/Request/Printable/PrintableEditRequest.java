package com.builpr.restapi.Request.Printable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * EditRequest for the Printable
 */
public class PrintableEditRequest {

    /**
     * the token from the user
     */
    @Getter
    @Setter
    String token;

    /**
     * printableID
     */
    @Getter
    @Setter
    int printableID;

    /**
     * title
     */
    @Getter
    @Setter
    String title;

    /**
     * printable desciption
     */
    @Getter
    @Setter
    String description;

    /**
     * list of tags
     */
    @Getter
    @Setter
    List<String> categories;

}
