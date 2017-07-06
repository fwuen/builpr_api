package com.builpr.restapi.utils;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.restapi.converter.PrintableToSolrPrintableConverter;
import com.builpr.search.SearchManagerException;
import com.builpr.search.model.Indexable;
import com.builpr.search.solr.SolrSearchManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus Goller
 */
public class PrintableSolrHelper {
    private DatabasePrintableManager databasePrintableManager;

    public PrintableSolrHelper() {
        databasePrintableManager = new DatabasePrintableManager();
    }

    /**
     * Index every Printable
     *
     * @return void
     * @throws SearchManagerException exception
     */
    public void indexPrintables() throws SearchManagerException {
        List<Printable> printableList = databasePrintableManager.getAllPrintables();
        List<Indexable> solrPrintableList = new ArrayList<>();
        for (Printable printable : printableList) {
            com.builpr.search.model.Printable solrPrintable = PrintableToSolrPrintableConverter.getSolrPrintable(printable);
            solrPrintableList.add(solrPrintable);
        }
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        if (!solrPrintableList.isEmpty()) {
            solrSearchManager.addToIndex(solrPrintableList);
        }
    }

    /**
     * Index a single Printable
     * @param printable Printabale
     * @throws SearchManagerException exception
     */
    public void addPrintableToIndex(Printable printable) throws SearchManagerException {
        com.builpr.search.model.Printable solrPrintable = PrintableToSolrPrintableConverter.getSolrPrintable(printable);
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("http://192.168.1.50:8983/solr");
        solrSearchManager.addToIndex(solrPrintable);
    }

    /**
     * Get the average Rating a Printable has
     *
     * @param ratings List<Rating>
     * @return double
     */
    public double getAverageRating(List<Rating> ratings) {
        double average = 0.0;
        int ratingCounter = 0;
        for (Rating rating : ratings) {
            average = +rating.getRating();
            ratingCounter++;
        }
        if (ratingCounter == 0) {
            return 0;
        }
        average = average / ratingCounter;
        return average;
    }
}
