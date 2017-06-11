package com.builpr.restapi.model.Request.Printable;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * EditRequest for the Printable
 */
public class PrintableEditRequest {

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
