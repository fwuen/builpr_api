package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;

import java.util.List;

/**
 * @author Markus Goller
 *
 * PrintableToPrintableNewResponseConverter
 */
public class PrintableToPrintableNewResponseConverter {

    /**
     * Converting a Printable to PrintableNewResponse
     *
     * @param printable    Printable
     * @param categoryList List<Category>
     * @return PrintableNewResponse
     */
    public static PrintableNewResponse from(Printable printable, List<Category> categoryList) {
        PrintableNewResponse printableNewResponse = new PrintableNewResponse();
        printableNewResponse.setPrintableID(printable.getPrintableId());
        printableNewResponse.setOwnerID(printable.getUploaderId());
        if (printable.getDescription().isPresent()) {
            printableNewResponse.setDescription(printable.getDescription().get());
        }
        printableNewResponse.setTitle(printable.getTitle());
        printableNewResponse.setCategories(CategoryToStringConverter.convertCategoriesToString(categoryList));
        printableNewResponse.setUploadDate(printable.getUploadTime());
        return printableNewResponse;
    }
}
