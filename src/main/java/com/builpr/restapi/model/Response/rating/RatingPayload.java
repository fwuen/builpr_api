package com.builpr.restapi.model.Response.rating;

import lombok.Getter;

/**
 * rating payload
 */
public class RatingPayload {

    @Getter
    private int ownerID;

    @Getter
    private int rating;

    @Getter
    private String text;

    @Getter
    private String time;

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
