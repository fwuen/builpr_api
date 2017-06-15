package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.RatingModelToRatingPayloadConverter;
import com.builpr.restapi.error.rating.RatingDeleteError;
import com.builpr.restapi.error.rating.RatingNewError;
import com.builpr.restapi.model.Request.Rating.RatingDeleteRequest;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.rating.RatingPayload;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

/**
 * RatingController
 */
@RestController
public class RatingController {

    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseRatingManager databaseRatingManager;

    public RatingController() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databaseRatingManager = new DatabaseRatingManager();
    }


    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/rating/new", method = RequestMethod.POST)
    @ResponseBody
    public Response<RatingPayload> createRating(Principal principal, @RequestBody RatingNewRequest request) {
        Response<RatingPayload> response = new Response<>();
        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(RatingNewError.PRINTABLE_NOT_EXISTING);
        }

        if (principal != null) {
            if (databaseUserManager.getByUsername(principal.getName()) == null) {
                response.setSuccess(false);
                response.addError(RatingNewError.NO_AUTHORIZATION);
            } else if (request.getPrintableID() == databaseUserManager.getByUsername(principal.getName()).getUserId()) {
                response.setSuccess(false);
                response.addError(RatingNewError.NO_AUTHORIZATION);
            } else if (databaseRatingManager.getRatingByIds(request.getPrintableID(), databaseUserManager.getByUsername(principal.getName()).getUserId()) != null) {
                response.setSuccess(false);
                response.addError(RatingNewError.RATING_ALREADY_EXISTS);
            }
        } else {
            response.setSuccess(false);
            response.addError(RatingNewError.NO_AUTHORIZATION);
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            response.setSuccess(false);
            response.addError(RatingNewError.RATING_INVALID);
        }
        if (request.getText().length() < 5) {
            response.setSuccess(false);
            response.addError(RatingNewError.TEXT_INVALID);
        }
        if (!response.isSuccess()) {
            return response;
        }
        User user = null;
        if (principal != null) {
            user = databaseUserManager.getByUsername(principal.getName());
        }

        if (user != null) {
            databaseRatingManager.createRating(request, user.getUserId());

            Rating rating = databaseRatingManager.getRatingByIds(request.getPrintableID(), user.getUserId());
            RatingPayload payload = RatingModelToRatingPayloadConverter.from(rating);
            response.setPayload(payload);
        }
        return response;
    }

    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/rating/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<RatingPayload> deleteRating(Principal principal, @RequestParam(
            value = "id",
            defaultValue = "0"
    ) int ratingID) {
        Response<RatingPayload> response = new Response<>();
        if (ratingID == 0 || databaseRatingManager.getRatingByRatingID(ratingID) == null) {
            response.setSuccess(false);
            response.addError(RatingDeleteError.RATING_NOT_FOUND);
            return response;
        }
        if (principal != null) {
            if (databaseUserManager.getByUsername(principal.getName()) == null) {
                response.setSuccess(false);
                response.addError(RatingDeleteError.NO_AUTHORIZATION);
            } else if (databaseRatingManager.getRatingByRatingID(ratingID).getUserId() == databaseUserManager.getByUsername(principal.getName()).getUserId()) {
                response.setSuccess(false);
                response.addError(RatingDeleteError.NO_AUTHORIZATION);
            }
        } else {
            response.setSuccess(false);
            response.addError(RatingDeleteError.NO_AUTHORIZATION);
        }
        if (!response.isSuccess()) {
            return response;
        }
        Rating rating = databaseRatingManager.getRatingByRatingID(ratingID);
        databaseRatingManager.deleteRating(ratingID);
        RatingPayload payload = RatingModelToRatingPayloadConverter.from(rating);
        response.setPayload(payload);
        return response;
    }
}

