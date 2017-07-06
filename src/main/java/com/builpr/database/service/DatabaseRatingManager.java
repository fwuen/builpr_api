package com.builpr.database.service;


import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Markus Goller
 * rating service
 */
public class DatabaseRatingManager extends DatabaseManager<RatingManager> {

    public DatabaseRatingManager() {
        super(RatingManager.class);
    }

    /**
     * Check if a Rating is already existing in the database
     *
     * @param ratingID int
     * @return boolean
     */
    public boolean isPresent(int ratingID) {
        return getRatingByRatingID(ratingID) != null;
    }

    /**
     * Returning every Rating referring to a Printable
     *
     * @param printableID int
     * @return List<rating>
     */
    public List<Rating> getRatingsForPrintable(int printableID) {
        return getDao().stream().filter(Rating.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

    /**
     * Persistently saving a Rating in the database
     *
     * @param rating Rating
     * @return void
     */
    public Rating persist(Rating rating) {
        return getDao().persist(rating);
    }

    /**
     * Return a Rating by the id of the User and the Printable
     *
     * @param printableID int
     * @param userID      int
     * @return rating
     */
    public Rating getRatingByIds(int printableID, int userID) {
        Optional<Rating> list = getDao().stream().filter(Rating.PRINTABLE_ID.equal(printableID)).filter(Rating.USER_ID.equal(userID)).findFirst();
        return list.orElse(null);
    }

    /**
     * Return a Rating by the in the database
     *
     * @param ratingID int
     * @return rating
     */
    public Rating getRatingByRatingID(int ratingID) {
        Optional<Rating> list = getDao().stream().filter(Rating.RATING_ID.equal(ratingID)).findFirst();
        return list.orElse(null);
    }

    /**
     * Deleting a Rating from database
     *
     * @param ratingId int
     * @return void
     */
    public void deleteRatingByID(int ratingId) {
        getDao().remove(
                this.getRatingByRatingID(ratingId)
        );
    }
}
