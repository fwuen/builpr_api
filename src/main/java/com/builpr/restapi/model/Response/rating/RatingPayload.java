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
}
