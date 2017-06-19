package com.builpr.restapi.utils;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.restapi.converter.PrintableToSolrPrintableConverter;
import com.builpr.search.SearchManagerException;
import com.builpr.search.model.Indexable;
import com.builpr.search.solr.SolrSearchManager;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PrintableIndexing {
    private DatabasePrintableManager databasePrintableManager;

    public PrintableIndexing() {
        databasePrintableManager = new DatabasePrintableManager();
    }

    /**
     * @return void
     * @throws SearchManagerException exception
     */
    public void indexPrintables() throws SearchManagerException {
        List<Printable> printables = databasePrintableManager.getAllPrintables();
        List<Indexable> solrPrintableList = new ArrayList<>();
        for (Printable printable : printables) {
            com.builpr.search.model.Printable solrPrintable = PrintableToSolrPrintableConverter.getSolrPrintable(printable);
            solrPrintableList.add(solrPrintable);
        }
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        if (!solrPrintableList.isEmpty()) {
            solrSearchManager.addToIndex(solrPrintableList);
        }
    }

    /**
     * @return void
     */
    public void deletePrintableFromIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        solrSearchManager.clearIndex();

    }
}
