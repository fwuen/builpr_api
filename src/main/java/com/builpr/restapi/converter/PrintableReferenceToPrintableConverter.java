package com.builpr.restapi.converter;

import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.search.model.PrintableReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus Goller
 *
 * PrintableReferenceToPrintableConverter
 */
public class PrintableReferenceToPrintableConverter {
    private DatabasePrintableManager databasePrintableManager;

    public PrintableReferenceToPrintableConverter() {
        databasePrintableManager = new DatabasePrintableManager();
    }

    /**
     * Returns a list of PrintablePayloads converted from PrintableReferences
     *
     * @param printableReferences PrintableReference
     * @return List<Printable>
     */
    public List<PrintablePayload> getPrintableList(List<PrintableReference> printableReferences) {
        List<PrintablePayload> list = new ArrayList<>();
        for (PrintableReference reference : printableReferences) {
            if (databasePrintableManager.getPrintableById(Integer.parseInt(reference.getId())) != null) {
                list.add(PrintableModelToPrintablePayloadConverter.from(databasePrintableManager.getPrintableById(Integer.parseInt(reference.getId()))));
            }
        }
        return list;
    }
}
