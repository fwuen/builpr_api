package com.builpr.restapi.model.Response.printable;

import lombok.Getter;

import java.sql.Date;
import java.util.List;

/**
 * Response for the request to create a new printable
 */
public class PrintableNewResponse {

    @Getter
    private int printableID;

    @Getter
    private int ownerID;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private List<String> categories;

    @Getter
    private Date uploadDate;

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

    public PrintableNewResponse setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }
}
