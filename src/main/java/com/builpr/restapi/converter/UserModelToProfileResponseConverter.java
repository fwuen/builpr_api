package com.builpr.restapi.converter;


import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.user.User;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.utils.GravatarWrapper;
import java.util.*;

/**
 * maps a speedment user model to a profile payload model
 */
public class UserModelToProfileResponseConverter {

    private static DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();

    private static DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();

    private static DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();

    public static ProfilePayload from(User user) {

        GravatarWrapper gravatarWrapper = new GravatarWrapper();

        boolean showName = user.getShowName();
        boolean showEmail = user.getShowEmail();
        boolean showBirthday = user.getShowBirthday();

        int ratingcount = 0;
        int addedUpRating = 0;

        List<Printable> printablesForUser = databasePrintableManager.getPrintablesForUser(user.getUserId());

        // count all the ratings and add them up
        for (Printable printable:
             printablesForUser) {
            List<Rating> ratingsForPrintable = databaseRatingManager.getRatingsForPrintable(printable.getPrintableId());
            for (Rating rating :
                    ratingsForPrintable) {
                ratingcount++;
                addedUpRating += rating.getRating();
            }
        }

        List<PrintablePayload> printablePayloadList = new ArrayList<>();

        for (Printable printable :
                printablesForUser) {
            printablePayloadList.add(PrintableModelToPrintablePayloadConverter.from(printable));
        }

        return new ProfilePayload().setUserID(user.getUserId())
                .setUsername(user.getUsername())
                .setFirstname(showName ? user.getFirstname() : null)
                .setLastname(showName ? user.getLastname() : null)
                .setEmail(showEmail ? user.getEmail() : null)
                .setBirthday(showBirthday ? user.getBirthday().toString() : null)
                .setDescription(user.getDescription().isPresent() ? user.getDescription().get() : null)
                .setAvatarURL(gravatarWrapper.getUrl(user.getEmail()))
                .setPrintables(printablePayloadList)
                .setRating((double)addedUpRating / (double)ratingcount)
                .setRatingCount(ratingcount)
                .setRegistrationDate(user.getRegtime().toLocalDateTime().toString());
    }

}
