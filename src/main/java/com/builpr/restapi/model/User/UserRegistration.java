package com.builpr.restapi.model.User;


import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class UserRegistration {

    @Getter private String username;

    @Getter private String firstname;

    @Getter private String lastname;

    @Getter private String email;

    @Getter private String password;

    @Getter private int birthday;

    @Getter private String avatar;

    @Getter private String skype;

    @Getter private String twitter;

    @Getter private String facebook;

    @Getter private String instagram;

    @Getter private String description;

    @Getter private int showName;

    @Getter private int showBirthday;

    @Getter private int showEmail;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShowName(int showName) {
        this.showName = showName;
    }

    public void setShowBirthday(int showBirthday) {
        this.showBirthday = showBirthday;
    }

    public void setShowEmail(int showEmail) {
        this.showEmail = showEmail;
    }

    public User toUser() {
        return new UserImpl()
                .setUsername(username)
                .setFirstname(firstname)
                .setLastname(lastname)
                .setEmail(email)
                .setPassword(password)
                .setBirthday(new Date(birthday))
                .setAvatar(avatar)
                .setSkype(skype)
                .setTwitter(twitter)
                .setFacebook(facebook)
                .setInstagram(instagram)
                .setDescription(description)
                .setShowName(showName > 0)
                .setShowBirthday(showBirthday > 0)
                .setShowEmail(showEmail > 0)
                .setRegtime(new Timestamp(System.currentTimeMillis()));
    }

}
