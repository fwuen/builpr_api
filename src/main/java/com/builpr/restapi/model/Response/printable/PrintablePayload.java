package com.builpr.restapi.model.Response.printable;

import com.builpr.restapi.model.Response.rating.RatingPayload;
import lombok.Getter;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
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
    private String uploadTime;

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

    public PrintablePayload setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrintablePayload that = (PrintablePayload) o;

        if (printableID != that.printableID) return false;
        if (ownerID != that.ownerID) return false;
        if (downloads != that.downloads) return false;
        if (!title.equals(that.title)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!categories.equals(that.categories)) return false;
        if (ratings != null ? !ratings.equals(that.ratings) : that.ratings != null) return false;
        return uploadTime.equals(that.uploadTime);
    }

    @Override
    public int hashCode() {
        int result = printableID;
        result = 31 * result + ownerID;
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + categories.hashCode();
        result = 31 * result + downloads;
        result = 31 * result + (ratings != null ? ratings.hashCode() : 0);
        result = 31 * result + uploadTime.hashCode();
        return result;
    }
}
