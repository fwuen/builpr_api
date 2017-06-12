package com.builpr.restapi.model.Response.printable;

import com.builpr.restapi.model.Response.rating.RatingPayload;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

/**
 * printable payload
 */
public class PrintablePayload {

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
    private int downloads;

    @Getter
    private List<RatingPayload> ratings;

    @Getter
    private Date uploadDate;

    public PrintablePayload setPrintableID(int printableID) {
        this.printableID = printableID;
        return this;
    }

    public PrintablePayload setOwnerID(int ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public PrintablePayload setTitle(String title) {
        this.title = title;
        return this;
    }

    public PrintablePayload setDescription(String description) {
        this.description = description;
        return this;
    }

    public PrintablePayload setCategories(List<String> categories) {
        this.categories = categories;
        return this;
    }

    public PrintablePayload setDownloads(int downloads) {
        this.downloads = downloads;
        return this;
    }

    public PrintablePayload setRatings(List<RatingPayload> ratings) {
        this.ratings = ratings;
        return this;
    }

    public PrintablePayload setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }
}
