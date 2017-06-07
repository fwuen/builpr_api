package com.builpr.restapi.service;

import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.rating.RatingManager;
import com.builpr.restapi.utils.Connector;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rating service
 */
public class RatingService {
    private RatingManager ratingManager;

    public RatingService() {
        ratingManager = Connector.getConnection().getOrThrow(RatingManager.class);
    }

    public List<Rating> getRatingsForPrintable(int printableID) {
        return ratingManager.stream().filter(Rating.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

}
