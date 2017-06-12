package com.builpr.restapi.controller;

import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.RatingModelToRatingPayloadConverter;
import com.builpr.restapi.error.Rating.RatingDeleteError;
import com.builpr.restapi.error.Rating.RatingNewError;
import com.builpr.restapi.model.Request.Rating.RatingDeleteRequest;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.rating.RatingPayload;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

/**
 *
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


    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/rating/new", method = RequestMethod.POST)
    @ResponseBody
    public Response<RatingPayload, RatingNewError> createRating(Principal principal, @RequestBody RatingNewRequest request) {
        Response<RatingPayload, RatingNewError> response = new Response<>();
        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(RatingNewError.PRINTABLE_NOT_EXISTING);
        }

        if (principal != null) {
            if (databaseUserManager.getByUsername(principal.getName()) == null) {
                response.setSuccess(false);
                response.addError(RatingNewError.USER_INVALID);
            } else if (request.getPrintableID() == databaseUserManager.getByUsername(principal.getName()).getUserId()) {
                response.setSuccess(false);
                response.addError(RatingNewError.NO_AUTHORIZATION);
            } else if (databaseRatingManager.getRatingByIds(request.getPrintableID(), databaseUserManager.getByUsername(principal.getName()).getUserId()) != null) {
                response.setSuccess(false);
                response.addError(RatingNewError.RATING_ALREADY_EXISTS);
            }
        } else {
            response.setSuccess(false);
            response.addError(RatingNewError.USER_INVALID);
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            response.setSuccess(false);
            response.addError(RatingNewError.RATING_INVALID);
        }
        if (Objects.equals(request.getText(), "")) {
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

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/rating/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<RatingPayload, RatingDeleteError> createRating(Principal
                                                                           principal, @RequestBody RatingDeleteRequest request) {
        Response<RatingPayload, RatingDeleteError> response = new Response<>();
        if (request.getRatingID() == 0 || databaseRatingManager.getRatingByRatingID(request.getRatingID()) == null) {
            response.setSuccess(false);
            response.addError(RatingDeleteError.RATING_NOT_FOUND);
            return response;
        }
        if (principal != null) {
            if (databaseUserManager.getByUsername(principal.getName()) == null) {
                response.setSuccess(false);
                response.addError(RatingDeleteError.USER_INVALID);
            } else if (databaseRatingManager.getRatingByRatingID(request.getRatingID()).getUserId() == databaseUserManager.getByUsername(principal.getName()).getUserId()) {
                response.setSuccess(false);
                response.addError(RatingDeleteError.NO_AUTHORIZATION);
            }
        } else {
            response.setSuccess(false);
            response.addError(RatingDeleteError.USER_INVALID);
        }
        Rating rating = databaseRatingManager.getRatingByRatingID(request.getRatingID());
        databaseRatingManager.deleteRating(request.getRatingID());
        RatingPayload payload = RatingModelToRatingPayloadConverter.from(rating);
        response.setPayload(payload);
        return response;
    }
}

