package com.builpr.restapi.model.Response.profile;

import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.search.model.Printable;
import lombok.Getter;

import java.util.List;

/**
 * payload when displaying a user's profile
 */
public class ProfilePayload {

    @Getter
    private int userID;

    @Getter
    private String username;

    @Getter
    private String firstname;

    @Getter
    private String lastname;

    @Getter
    private String email;

    @Getter
    private String birthday;

    @Getter
    private String description;

    @Getter
    private String avatarURL;

    @Getter
    private double rating;

    @Getter
    private int ratingCount;

    @Getter
    private List<PrintablePayload> printables;

    @Getter
    private String registrationDate;

    public ProfilePayload setUserID(int userID) {
        this.userID = userID;
        return this;
    }

    public ProfilePayload setUsername(String username) {
        this.username = username;
        return this;
    }

    public ProfilePayload setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ProfilePayload setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ProfilePayload setEmail(String email) {
        this.email = email;
        return this;
    }

    public ProfilePayload setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public ProfilePayload setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProfilePayload setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public ProfilePayload setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public ProfilePayload setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
        return this;
    }

    public ProfilePayload setPrintables(List<PrintablePayload> printables) {
        this.printables = printables;
        return this;
    }

    public ProfilePayload setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }
}