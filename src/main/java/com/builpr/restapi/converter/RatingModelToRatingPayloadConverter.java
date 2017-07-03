package com.builpr.restapi.converter;

import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.rating.RatingPayload;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * converts a speedment rating model to a rating payload
 */
public class RatingModelToRatingPayloadConverter {

    /**
     * Returns the RatingPayload converted from a Rating
     * @param rating Rating
     * @return RatingPayload
     */
    public static RatingPayload from(Rating rating) {
        DatabaseUserManager userManager = new DatabaseUserManager();
        String ownerName = userManager.getByID(rating.getUserId()).getUsername();
        Optional<String> gravatar = userManager.getByID(rating.getUserId()).getAvatar();
        String ownerGravatarURL = gravatar.orElse("");

        return new RatingPayload()
                .setOwnerID(rating.getUserId())
                .setRating(rating.getRating())
                .setText(rating.getMsg().isPresent() ? rating.getMsg().get() : null)
                .setTime(rating.getRatingTime().toString())
                .setOwnerUserName(ownerName)
                .setOwnerGravatarURL(ownerGravatarURL);
    }

    /**
     * Converts a list of Ratings
     *
     * @param ratings List<Rating>
     * @return List<RatingPayload>
     */
    public static List<RatingPayload> from(List<Rating> ratings) {
        List<RatingPayload> ratingPayloads = new ArrayList<>();
        for (Rating rating : ratings) {
            ratingPayloads.add(from(rating));
        }
        return ratingPayloads;
    }
}
