package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.restapi.model.Response.rating.RatingPayload;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;

import java.util.ArrayList;
import java.util.List;


/**
 * converts a printable speedment model to a printable payload
 */
public class PrintableModelToPrintablePayloadConverter {

    public static PrintablePayload from(Printable printable) {

        DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
        DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();

        List<Category> categoriesForPrintable = databaseCategoryManager.getCategoriesForPrintable(printable.getPrintableId());


        List<String> categoryNames = new ArrayList<>();

        for (Category category :
                categoriesForPrintable) {
            categoryNames.add(category.getCategoryName());

        }

        List<Rating> ratingList = databaseRatingManager.getRatingsForPrintable(printable.getPrintableId());
        List<RatingPayload> ratingPayloadList = new ArrayList<>();

        for (Rating rating :
                ratingList) {
            ratingPayloadList.add(RatingModelToRatingPayloadConverter.from(rating));
        }

        return new PrintablePayload()
                .setPrintableID(printable.getPrintableId())
                .setOwnerID(printable.getUploaderId())
                .setTitle(printable.getTitle())
                .setDescription(printable.getDescription().isPresent() ? printable.getDescription().get() : null)
                .setCategories(categoryNames)
                .setRatings(ratingPayloadList)
                .setDownloads(printable.getNumDownloads())
                .setUploadTime(printable.getUploadTime().toString());
    }

}
