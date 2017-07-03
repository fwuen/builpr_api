package com.builpr.restapi.model.Response.printable;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

/**
 * Response for the request to create a new printable
 */
public class PrintableNewResponse {

    /**
     * Id of the Printable
     */
    @Getter
    private int printableID;

    /**
     * Id of the User
     */
    @Getter
    private int ownerID;

    /**
     * Title of the Printable
     */
    @Getter
    private String title;
    /**
     * Description of the Printable
     */
    @Getter
    private String description;

    /**
     * List of Categories the Printable has
     */
    @Getter
    private List<String> categories;

    /**
     * Time the Printable got uploaded
     */
    @Getter
    private Timestamp uploadDate;

    public PrintableNewResponse setPrintableID(int printableID) {
        this.printableID = printableID;
        return this;
    }

    public PrintableNewResponse setOwnerID(int ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public PrintableNewResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public PrintableNewResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public PrintableNewResponse setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public PrintableNewResponse setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }
}
