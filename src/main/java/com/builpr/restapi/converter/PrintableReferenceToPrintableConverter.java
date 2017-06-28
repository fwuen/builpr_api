package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.search.model.PrintableReference;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PrintableReferenceToPrintableConverter {
    private DatabasePrintableManager databasePrintableManager;

    public PrintableReferenceToPrintableConverter() {
        databasePrintableManager = new DatabasePrintableManager();
    }

    /**
     * @param printableReferences PrintableReference
     * @return List<Printable>
     * TODO refactoren bzw name ändern
     */
    public List<Printable> getPrintableList(List<PrintableReference> printableReferences) {
        List<Printable> list = new ArrayList<>();
        for (PrintableReference reference : printableReferences) {
            if (databasePrintableManager.getPrintableById(Integer.parseInt(reference.getId())) != null) {
                list.add(databasePrintableManager.getPrintableById(Integer.parseInt(reference.getId())));
            }
        }
        return list;
    }
}
