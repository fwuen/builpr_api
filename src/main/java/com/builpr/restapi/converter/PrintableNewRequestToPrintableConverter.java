package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;

import java.sql.Date;

/**
 *
 */
public class PrintableNewRequestToPrintableConverter {


    public static Printable from(PrintableNewRequest request, int userID, String path) {
        PrintableImpl printable = new PrintableImpl();
        printable.setTitle(request.getTitle());
        printable.setDescription(request.getDescription());
        printable.setUploaderId(userID);
        printable.setFilePath(path);

        return printable;
    }
}
