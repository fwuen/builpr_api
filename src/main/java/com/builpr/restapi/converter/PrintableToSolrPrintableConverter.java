package com.builpr.restapi.converter;

import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.search.model.Printable;

import java.util.Date;

/**
 *
 */
public class PrintableToSolrPrintableConverter {

    public static Printable getSolrPrintable(com.builpr.database.bridge.printable.Printable printable) {
        DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();
        DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
        double rating = databaseRatingManager.getAverageRating(databaseRatingManager.getRatingsForPrintable(printable.getPrintableId()));
        String description = "";
        if (printable.getDescription().isPresent()) {
            description = printable.getDescription().get();
        }

        return Printable.getBuilder()
                .withCategories(CategoryToStringConverter.convertCategoriesToString(databaseCategoryManager.getCategoriesByID(printable.getPrintableId())))
                .withDescription(description)
                .withId(printable.getPrintableId())
                .withNumberOfDownloads(printable.getNumDownloads())
                .withRating(rating)
                .withTitle(printable.getTitle())
                .withUploadDate(new Date(printable.getUploadTime().getTime()))
                .withUploaderId(printable.getUploaderId())
                .build();
    }
}
