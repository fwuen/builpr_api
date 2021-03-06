package com.builpr.restapi.model.Response.rating;

import com.builpr.database.bridge.rating.Rating;
import lombok.Getter;

/**
 * @author Markus Goller
 *
 * rating payload
 */
public class RatingPayload {

    /**
     * Id of the User
     */
    @Getter
    private int ownerID;

    /**
     * Value of the Rating
     */
    @Getter
    private int rating;

    /**
     * Text of the Rating
     */
    @Getter
    private String text;

    /**
     * Time the Rating got created
     */
    @Getter
    private String time;

    @Getter
    private String ownerUserName;

    @Getter
    private String ownerGravatarURL;

    public RatingPayload setOwnerID(int ownerID) {
        this.ownerID = ownerID;
        return this;
    }

    public RatingPayload setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public RatingPayload setText(String text) {
        this.text = text;
        return this;
    }

    public RatingPayload setTime(String time) {
        this.time = time;
        return this;
    }

    public RatingPayload setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
        return this;
    }

    public RatingPayload setOwnerGravatarURL(String ownerGravatarURL) {
        this.ownerGravatarURL = ownerGravatarURL;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingPayload that = (RatingPayload) o;

        if (ownerID != that.ownerID) return false;
        if (rating != that.rating) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result = ownerID;
        result = 31 * result + rating;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + time.hashCode();
        return result;
    }
}
