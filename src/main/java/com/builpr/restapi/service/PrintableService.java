package com.builpr.restapi.service;

import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printable.PrintableManager;
import com.builpr.restapi.utils.Connector;


import java.util.List;
import java.util.stream.Collectors;

/**
 * printable service
 */
public class PrintableService {

    private PrintableManager printableManager;

    public PrintableService() {
        printableManager = Connector.getConnection().getOrThrow(PrintableManager.class);
    }

    public List<Printable> getPrintablesForUser(int userID) {
        return printableManager.stream().filter(Printable.UPLOADER_ID.equal(userID)).collect(Collectors.toList());
    }
}
