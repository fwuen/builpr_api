package com.builpr.restapi.model;

import com.builpr.database.db.builpr.user.User;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

// @todo statt ints bei den flags evtl booleans

public class PublicUser {

    @Getter private int uid;

    @Getter private String username;

    @Getter private String firstname;

    @Getter private String lastname;

    @Getter private String email;

    @Getter private Date birthday;

    @Getter private Timestamp regtime;

    @Getter private String avatar;

    @Getter private String skype;

    @Getter private String twitter;

    @Getter private String facebook;

    @Getter private String instagram;

    @Getter private String description;


    public PublicUser(User user) {
        uid = user.getUid();
        username = user.getUsername();

        if(user.getShowEmail() == 1) {
            email = user.getEmail();
        }

        regtime = user.getRegtime();
        if(user.getShowBirthday() == 1) {
            birthday = user.getBirthday();
        }

        if(user.getShowName() == 1) {
            firstname = user.getFirstname();
            lastname = user.getLastname();
        }

        if(user.getAvatar().isPresent()) {
            avatar = user.getAvatar().get();
        }

        if(user.getSkype().isPresent()) {
            skype = user.getSkype().get();
        }

        if(user.getTwitter().isPresent()) {
            twitter = user.getTwitter().get();
        }

        if(user.getFacebook().isPresent()) {
            facebook = user.getFacebook().get();
        }

        if(user.getInstagram().isPresent()) {
            instagram = user.getInstagram().get();
        }

        if(user.getDescription().isPresent()) {
            description = user.getDescription().get();
        }
    }
}
