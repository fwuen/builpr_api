package com.builpr.restapi.model.Request.Rating;

import lombok.Getter;
import lombok.Setter;

/**
 * RatingNewRequest
 */
public class RatingNewRequest {

    /**
     * printable-id
     */
    @Getter
    @Setter
    private int printableID;

    /**
     * rating-value
     */
    @Getter
    @Setter
    private int rating;

    /**
     * test
     */
    @Getter
    @Setter
    private String text;
}
