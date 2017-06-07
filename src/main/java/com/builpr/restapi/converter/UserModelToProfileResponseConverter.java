package com.builpr.restapi.converter;

import com.builpr.database.db.builpr.category.Category;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.rating.Rating;
import com.builpr.database.db.builpr.user.User;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.builpr.restapi.service.CategoryService;
import com.builpr.restapi.service.PrintableService;
import com.builpr.restapi.service.RatingService;
import com.builpr.restapi.utils.GravatarWrapper;
import java.util.*;

/**
 * maps a speedment user model to a profile payload model
 */
public class UserModelToProfileResponseConverter {

    private static PrintableService printableService;

    private static RatingService ratingService;

    private static CategoryService categoryService;

    public UserModelToProfileResponseConverter() {
        printableService = new PrintableService();
        ratingService = new RatingService();
        categoryService = new CategoryService();
    }

    public static ProfilePayload from(User user) {

        GravatarWrapper gravatarWrapper = new GravatarWrapper();

        boolean showName = user.getShowName();
        boolean showEmail = user.getShowEmail();
        boolean showBirthday = user.getShowBirthday();

        int ratingcount = 0;
        int addedUpRating = 0;


        List<Printable> printablesForUser = printableService.getPrintablesForUser(user.getUserId());

        Map<Printable, List<Category>> categoriesForUser = categoryService.getCategoriesForUser(user.getUserId());

        List<Category> categories = new ArrayList<>();

        // create a list with every category
        for (Map.Entry<Printable, List<Category>> entry :
                categoriesForUser.entrySet()) {
            categories.addAll(entry.getValue());
        }

        Map<String, Integer> countCategoriesMap = new HashMap<>(10);

        //
        for (Category category :
                categories) {
            countCategoriesMap.put(category.getCategoryName(), (countCategoriesMap.getOrDefault(category.getCategoryName(), 0)));
        }

        List<String> topCategories = new ArrayList<String>(countCategoriesMap.keySet());

        Collections.sort(topCategories, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return countCategoriesMap.get(o2) - countCategoriesMap.get(o1);
            }
        });

        // count all the ratings and add them up
        for (Printable printable:
             printablesForUser) {
            List<Rating> ratingsForPrintable = ratingService.getRatingsForPrintable(printable.getPrintableId());
            for (Rating rating :
                    ratingsForPrintable) {
                ratingcount++;
                addedUpRating += rating.getRating();
            }
        }


        return new ProfilePayload().setUserID(user.getUserId())
                .setUsername(user.getUsername())
                .setFirstname(showName ? user.getFirstname() : null)
                .setLastname(showName ? user.getLastname() : null)
                .setEmail(showEmail ? user.getEmail() : null)
                .setBirthday(showBirthday ? user.getBirthday().toString() : null)
                .setDescription(user.getDescription().isPresent() ? user.getDescription().get() : null)
                .setAvatarURL(gravatarWrapper.getUrl(user.getEmail()))
                .setRating(ratingcount/addedUpRating)
                .setRatingCount(ratingcount)
                .setTopCategories(topCategories)
                .setRegistrationDate(user.getRegtime().toLocalDateTime().toString());
    }

}
