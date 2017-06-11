package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Response.printable.PrintableEditResponse;
import com.builpr.restapi.model.Response.rating.RatingPayload;

import java.util.List;

/**
 *
 */
public class PrintableEditRequestToResponseConverter {

    private static DatabaseRatingManager databaseRatingManager;
    private static DatabaseCategoryManager databaseCategoryManager;
    private static DatabasePrintableManager databasePrintableManager;

    public PrintableEditRequestToResponseConverter() {
        databaseRatingManager = new DatabaseRatingManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        databasePrintableManager = new DatabasePrintableManager();
    }


    public static PrintableEditResponse from(PrintableEditRequest request) {
        PrintableEditResponse response = new PrintableEditResponse();
        Printable printable = databasePrintableManager.getPrintableById(request.getPrintableID());

        List<String> categories = CategoryToStringConverter.convertCategoriesToString(databaseCategoryManager.getCategoriesForPrintable(request.getPrintableID()));
        List<RatingPayload> ratings = RatingModelToRatingPayloadConverter.from(databaseRatingManager.getRatingsForPrintable(request.getPrintableID()));
        response.setPrintableID(printable.getPrintableId());
        response.setOwnerID(printable.getUploaderId());
        response.setTitle(printable.getTitle());
        if (printable.getDescription().isPresent()) {
            response.setDescription(printable.getDescription().get());
        }
        response.setDownloads(printable.getNumDownloads());
        response.setRatings(ratings);
        response.setCategories(categories);
        return response;
    }
}
