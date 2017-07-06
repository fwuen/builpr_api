package com.builpr.restapi.model.Request.Rating;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Markus Goller
 *
 * RatingNewRequest
 */
public class RatingNewRequest {

    /**
     * Id of the existing Printabl3
     */
    @Getter
    @Setter
    private int printableID;

    /**
     * Value of the new Rating
     */
    @Getter
    @Setter
    private int rating;

    /**
     * Text of the new Rating
     */
    @Getter
    @Setter
    private String text;
}
