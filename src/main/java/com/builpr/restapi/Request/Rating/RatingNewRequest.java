package com.builpr.restapi.Request.Rating;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Request to create a new rating
 */
public class RatingNewRequest {
    /**
     * user token
     */
    @Getter
    @Setter
    String token;

    /**
     * printableID
     */
    @Getter
    @Setter
    int printableID;

    /**
     * rating of the printable
     * <p>
     * can be between 1 and 5
     */
    @Getter
    int rating;

    /**
     * rating text
     */
    @Getter
    @Setter
    String text;

    /**
     * custom Setter to check if rating is in the allowed range
     *
     * @param aNumber int
     * @throws IllegalArgumentException Exception
     */
    public void setRating(@NonNull int aNumber) {
        if (aNumber >= 1 && aNumber <= 5) {
            this.rating = aNumber;
        } else {
            throw new IllegalArgumentException("value of rating is out of range");
        }
    }
}
