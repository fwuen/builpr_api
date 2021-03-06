package com.builpr.restapi.model.Request.Rating;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Markus Goller
 *
 * RatingDeleteRequest
 */
public class RatingDeleteRequest {
    /**
     * rating id
     */
    @Getter
    @Setter
    private int ratingID;

    /**
     * confirmation-flag
     */
    @Getter
    @Setter
    private boolean confirmation;
}
