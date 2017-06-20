package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.model.Response.printable.PrintableDeleteResponse;

/**
 *
 */
public class PrintableDeleteRequestToPrintableDeleteResponseConverter {

    private static DatabaseRatingManager databaseRatingManager;
    private static DatabaseCategoryManager databaseCategoryManager;

    public PrintableDeleteRequestToPrintableDeleteResponseConverter() {
        databaseRatingManager = new DatabaseRatingManager();
        databaseCategoryManager = new DatabaseCategoryManager();
    }

    public static PrintableDeleteResponse from(Printable printable) {
        PrintableDeleteResponse response = new PrintableDeleteResponse();
        response.setPrintableID(printable.getPrintableId());
        response.setOwnerID(printable.getUploaderId());
        if (printable.getDescription().isPresent()) {
            response.setDescription(printable.getDescription().get());
        }
        DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
        DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();
        response.setUploadTime(printable.getUploadTime().toString());
        response.setCategories(CategoryToStringConverter.convertCategoriesToString(databaseCategoryManager.getCategoriesForPrintable(printable.getPrintableId())));
        response.setTitle(printable.getTitle());
        response.setDownloads(printable.getNumDownloads());
        response.setRatings(RatingModelToRatingPayloadConverter.from(databaseRatingManager.getRatingsForPrintable(printable.getPrintableId())));

        return response;
    }
}