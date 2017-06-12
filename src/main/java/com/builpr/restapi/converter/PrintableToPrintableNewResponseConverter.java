package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;

/**
 *
 */
public class PrintableToPrintableNewResponseConverter {
    private static DatabaseCategoryManager databaseCategoryManager;
    private static DatabaseRatingManager databaseRatingManager;

    public PrintableToPrintableNewResponseConverter() {
        databaseCategoryManager = new DatabaseCategoryManager();
        databaseRatingManager = new DatabaseRatingManager();
    }

    public static PrintableNewResponse from(Printable printable) {

        PrintableNewResponse printableNewResponse = new PrintableNewResponse();
        printableNewResponse.setPrintableID(printable.getPrintableId());
        printableNewResponse.setOwnerID(printable.getUploaderId());
        if (printable.getDescription().isPresent()) {
            printableNewResponse.setDescription(printable.getDescription().get());
        }
        printableNewResponse.setTitle(printable.getTitle());
        printableNewResponse.setUploadDate(printable.getUploadDate());
        return printableNewResponse;
    }
}
