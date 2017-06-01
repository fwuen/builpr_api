package com.builpr.restapi.Request.Rating;

import lombok.Getter;
import lombok.Setter;

/**
 * Request to delete a rating
 */
public class RatingDeleteRequest {

    /**
     * user token
     */
    @Getter
    @Setter
    String token;

    /**
     * ratingID
     */
    @Getter
    @Setter
    int ratingID;

    /**
     * confirmation
     */
    @Getter
    @Setter
    boolean confirmation;
}
