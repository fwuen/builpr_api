package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;

import java.sql.Date;
import java.util.List;

/**
 * Converting a printable to printablenewresponse
 */
public class PrintableToPrintableNewResponseConverter {

    public static PrintableNewResponse from(Printable printable, List<Category> categoryList) {
        PrintableNewResponse printableNewResponse = new PrintableNewResponse();
        printableNewResponse.setPrintableID(printable.getPrintableId());
        printableNewResponse.setOwnerID(printable.getUploaderId());
        if (printable.getDescription().isPresent()) {
            printableNewResponse.setDescription(printable.getDescription().get());
        }
        printableNewResponse.setTitle(printable.getTitle());
        printableNewResponse.setUploadDate(new Date(printable.getUploadTime().getTime()));
        printableNewResponse.setCategories(CategoryToStringConverter.convertCategoriesToString(categoryList));

        return printableNewResponse;
    }
}
