package com.builpr.restapi.converter;

import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.rating.RatingPayload;

import java.util.ArrayList;
import java.util.List;

/**
 * converts a speedment rating model to a rating payload
 */
public class RatingModelToRatingPayloadConverter {


    public static RatingPayload from(Rating rating) {
        DatabaseUserManager userManager = new DatabaseUserManager();
        String ownerName = userManager.getByID(rating.getUserId()).getUsername();
        String ownerGravatarURL = userManager.getByID(rating.getUserId()).getAvatar().get();

        return new RatingPayload()
                .setOwnerID(rating.getUserId())
                .setRating(rating.getRating())
                .setText(rating.getMsg().isPresent() ? rating.getMsg().get() : null)
                .setTime(rating.getRatingTime().toString())
                .setOwnerUserName(ownerName)
                .setOwnerGravatarURL(ownerGravatarURL);
    }

    public static List<RatingPayload> from(List<Rating> ratings) {
        List<RatingPayload> ratingPayloads = new ArrayList<>();
        for (Rating rating : ratings) {
            ratingPayloads.add(from(rating));
        }
        return ratingPayloads;
    }
}
