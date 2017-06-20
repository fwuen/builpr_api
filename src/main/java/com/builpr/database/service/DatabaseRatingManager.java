package com.builpr.database.service;


import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.rating.RatingManager;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;

import javax.swing.text.html.Option;
import java.sql.Date;
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
     * @param userID      int
     * @return boolean
     */
    public boolean isPresentByIDs(int printableID, int userID) {
        return getRatingByIds(printableID, userID) != null;
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
    public void persist(Rating rating) {
        getDao().persist(rating);
    }

    /**
     * @param request RatingNewRequest
     * @param userID  int
     */
    public void createRating(RatingNewRequest request, int userID) {
        RatingImpl rating = new RatingImpl();
        rating.setMsg(request.getText());
        rating.setPrintableId(request.getPrintableID());
        rating.setRating(request.getRating());
        rating.setUserId(userID);

        persist(rating);
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
