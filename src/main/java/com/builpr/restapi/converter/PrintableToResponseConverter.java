package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.converter.CategoryToStringConverter;
import com.builpr.restapi.converter.RatingModelToRatingPayloadConverter;
import com.builpr.restapi.model.Response.printable.PrintableResponse;
import com.builpr.restapi.model.Response.rating.RatingPayload;

import java.util.List;

/**
 *
 */
public class PrintableToResponseConverter {

    public PrintableToResponseConverter() {
    }

    /**
     * Convert a Printable object to an PrintableResponse object
     *
     * @param printable Printable
     * @return PrintableResponse
     */
    public static PrintableResponse from(Printable printable) {
        DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
        DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();
        List<String> categories = CategoryToStringConverter.convertCategoriesToString(databaseCategoryManager.getCategoriesByID(printable.getPrintableId()));

        List<RatingPayload> ratings = RatingModelToRatingPayloadConverter.from(databaseRatingManager.getRatingsForPrintable(printable.getPrintableId()));
        PrintableResponse response = new PrintableResponse();
        response.setPrintableID(printable.getPrintableId());
        response.setOwnerID(printable.getUploaderId());
        response.setTitle(printable.getTitle());
        if (printable.getDescription().isPresent()) {
            response.setDescription(printable.getDescription().get());
        }
        response.setDownloads(printable.getNumDownloads());
        response.setUploadDate(printable.getUploadDate());
        response.setRatings(ratings);
        response.setCategories(categories);

        return response;
    }
}
