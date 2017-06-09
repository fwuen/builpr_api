package com.builpr.restapi.converter;

import com.builpr.database.bridge.rating.Rating;
import com.builpr.restapi.model.Response.rating.RatingPayload;

/**
 * converts a speedment rating model to a rating payload
 */
public class RatingModelToRatingPayloadConverter {


    public static RatingPayload from(Rating rating) {
        return new RatingPayload()
                .setOwnerID(rating.getUserId())
                .setRating(rating.getRating())
                .setText(rating.getMsg().isPresent() ? rating.getMsg().get() : null)
                .setTime(rating.getRatingTime().toString());
    }


}
