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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfilePayload that = (ProfilePayload) o;

        if (userID != that.userID) return false;
        if (Double.compare(that.rating, rating) != 0) return false;
        if (ratingCount != that.ratingCount) return false;
        if (!username.equals(that.username)) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (!avatarURL.equals(that.avatarURL)) return false;
        if (!printables.equals(that.printables)) return false;
        return registrationDate.equals(that.registrationDate);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userID;
        result = 31 * result + username.hashCode();
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + avatarURL.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ratingCount;
        result = 31 * result + printables.hashCode();
        result = 31 * result + registrationDate.hashCode();
        return result;
    }
}