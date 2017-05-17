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

        if(user.getShowEmail()) {
            email = user.getEmail();
        }

        regtime = user.getRegtime();
        if(user.getShowBirthday()) {
            birthday = user.getBirthday();
        }

        if(user.getShowName()) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PublicUser that = (PublicUser) o;

        if (uid != that.uid) return false;
        if (!username.equals(that.username)) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (!regtime.equals(that.regtime)) return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        if (skype != null ? !skype.equals(that.skype) : that.skype != null) return false;
        if (twitter != null ? !twitter.equals(that.twitter) : that.twitter != null) return false;
        if (facebook != null ? !facebook.equals(that.facebook) : that.facebook != null) return false;
        if (instagram != null ? !instagram.equals(that.instagram) : that.instagram != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = uid;
        result = 31 * result + username.hashCode();
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + regtime.hashCode();
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (skype != null ? skype.hashCode() : 0);
        result = 31 * result + (twitter != null ? twitter.hashCode() : 0);
        result = 31 * result + (facebook != null ? facebook.hashCode() : 0);
        result = 31 * result + (instagram != null ? instagram.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
