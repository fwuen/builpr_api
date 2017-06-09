package com.builpr.database.service;


import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingManager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * rating service
 */
public class DatabaseRatingManager extends DatabaseManager<RatingManager> {

    public DatabaseRatingManager() {
        super(RatingManager.class);
    }

    public List<Rating> getRatingsForPrintable(int printableID) {
        return getDao().stream().filter(Rating.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

}
