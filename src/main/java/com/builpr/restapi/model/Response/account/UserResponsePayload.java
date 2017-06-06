package com.builpr.restapi.model.Response.account;

import com.builpr.search.model.Printable;

import java.util.List;

/**
 * payload when displaying a user's profile
 */
public class UserResponsePayload {

    private int userID;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String birthday;

    private String description;

    private String avatarURL;

    private int rating;

    private int ratingCount;

    private List<Printable> printables;

    private List<String> topCategories;

    private String registrationDate;

}
