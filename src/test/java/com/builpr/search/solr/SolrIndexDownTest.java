package com.builpr.search.solr;

import com.builpr.Constants;
import com.builpr.search.SearchManagerException;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;
import com.google.common.base.Preconditions;
import org.assertj.core.util.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Felix Wünsche
 */
public class SolrIndexDownTest {
    @BeforeClass
    public static void buildIndexForDeletes() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        List<String> categories = Lists.newArrayList();
        List<Indexable> indexables = Lists.newArrayList();
        categories.add("justdelete");
        
        Date date = new Date(System.currentTimeMillis());
        
        Printable p1 = Printable.getBuilder().
                withId(1).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(0).
                build();
        Printable p2 = Printable.getBuilder().
                withId(2).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(0).
                build();
        Printable p3 = Printable.getBuilder().
                withId(3).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(0).
                build();
        
        indexables.add(p1);
        indexables.add(p2);
        indexables.add(p3);
        
        solrSearchManager.addToIndex(indexables);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteFromIndexSinglePrintableNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        PrintableReference printableReference = null;
        solrSearchManager.deleteFromIndex(printableReference);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteFromIndexMultiplePrintablesListNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        List<PrintableReference> list = null;
        solrSearchManager.deleteFromIndex(list);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteFromIndexMultiplePrintablesOneIsNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        List<PrintableReference> list = Lists.newArrayList();
        PrintableReference printableReference = null;
        list.add(printableReference);
        solrSearchManager.deleteFromIndex(list);
    }
    
    @Test
    public void deleteSinglePrintableFromIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        PrintableReference ref = new PrintableReference("1");
        solrSearchManager.deleteFromIndex(ref);
    }
    
    @Test
    public void deleteMultiplePrintablesFromIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        List<PrintableReference> refs = Lists.newArrayList();
        PrintableReference ref1 = new PrintableReference("2");
        PrintableReference ref2 = new PrintableReference("3");
        refs.add(ref1);
        refs.add(ref2);
        solrSearchManager.deleteFromIndex(refs);
    }
    
    @Test
    public void testClearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }

    //TODO delete mit exception
    //TODO clear mit exception
}
