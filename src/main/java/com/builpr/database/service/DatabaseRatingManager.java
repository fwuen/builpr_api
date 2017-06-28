package com.builpr.database.service;


import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * rating service
 */
public class DatabaseRatingManager extends DatabaseManager<RatingManager> {

    public DatabaseRatingManager() {
        super(RatingManager.class);
    }

    /**
     * @param ratingID int
     * @return boolean
     */
    public boolean isPresent(int ratingID) {
        return getRatingByRatingID(ratingID) != null;
    }

    /**
     * @param printableID int
     * @return List<rating>
     */
    public List<Rating> getRatingsForPrintable(int printableID) {
        return getDao().stream().filter(Rating.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

    /**
     * @param rating Rating
     * @return void
     */
    public Rating persist(Rating rating) {
        return getDao().persist(rating);
    }

    /**
     * @param printableID int
     * @param userID      int
     * @return rating
     */
    public Rating getRatingByIds(int printableID, int userID) {
        Optional<Rating> list = getDao().stream().filter(Rating.PRINTABLE_ID.equal(printableID)).filter(Rating.USER_ID.equal(userID)).findFirst();
        return list.orElse(null);
    }

    /**
     * @param ratingID int
     * @return rating
     */
    public Rating getRatingByRatingID(int ratingID) {
        Optional<Rating> list = getDao().stream().filter(Rating.RATING_ID.equal(ratingID)).findFirst();
        return list.orElse(null);
    }

    /**
     * @param ratingId int
     * @return void
     */
    public void deleteRatingByID(int ratingId) {
        getDao().remove(
                this.getRatingByRatingID(ratingId)
        );
    }
}
