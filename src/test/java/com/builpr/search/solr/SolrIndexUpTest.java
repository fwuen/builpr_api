package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;
import com.google.common.base.Preconditions;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Felix Wünsche
 */
public class SolrIndexUpTest {
    @Test
    public void indexRandomStringsWithCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = new ArrayList<String>();
        categories.add("egories");
        categories.add("cat");
        categories.add("awesome");
        categories.add("random");
        categories.add("very");
        categories.add("some");
        
        Date date = new Date(System.currentTimeMillis());
        
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        
        Printable p5 = Printable.getBuilder().
                withId(10).
                withTitle(SolrTestConstants.p5Title).
                withDescription(SolrTestConstants.p5Description).
                withRating(5).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(274422342).
                build();
        
        Preconditions.checkNotNull(p5);
        solrSearchManager.addToIndex(p5);
    }
    
    @Test
    public void indexWithCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        categories.add("3D");
        categories.add("car");
        categories.add("faberdashery");
        categories.add("makerbot");
        categories.add("PLA");
        categories.add("vehicle");
        
        Date date = new Date(System.currentTimeMillis());
        
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        
        Printable p1 = Printable.getBuilder().
                withId(1).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(27442).
                build();
        
        Preconditions.checkNotNull(p1);
        solrSearchManager.addToIndex(p1);
    }
    
    @Test
    public void indexWithMultipleFilesAndCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        Date date = new Date(System.currentTimeMillis());
        List<Indexable> indexables = new ArrayList<>();
        
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        Preconditions.checkNotNull(indexables);
        
        Printable p1 = Printable.getBuilder().
                withCategories(categories).
                withDescription("Tower").
                withId(2).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("Customizable tower").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        categories.add("plane");
        categories.add("ww1");
        Printable p2 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p2Description).
                withRating(5).
                withNumberOfDownloads(30).
                withId(3).
                withTitle("Gotha G.V").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        categories.clear();
        Printable p3 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p3Description).
                withRating(3).
                withNumberOfDownloads(526).
                withId(4).
                withTitle("Hookah/Shisha").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        Printable p4 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p4Description).
                withRating(2).
                withNumberOfDownloads(18340).
                withId(5).
                withUploadDate(date).
                withUploaderId(1).
                withTitle(SolrTestConstants.p4Title).
                build();
        
        indexables.add(p1);
        indexables.add(p2);
        indexables.add(p3);
        indexables.add(p4);
        
        Preconditions.checkNotNull(indexables);
        
        for (Indexable i : indexables)
            Preconditions.checkNotNull(i);
        
        solrSearchManager.addToIndex(indexables);
    }
    
    @Test
    public void indexWithMultipleFilesAndSameCategoriesAndCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        Date date = new Date(System.currentTimeMillis());
        List<Indexable> indexables = new ArrayList<>();
        
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        Preconditions.checkNotNull(indexables);
        
        categories.add("car");
        Printable p1 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car2").
                withId(6).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("car2").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        Printable p2 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car3").
                withRating(5).
                withNumberOfDownloads(30).
                withId(7).
                withTitle("car3").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        Printable p3 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car4").
                withRating(3).
                withNumberOfDownloads(526).
                withId(8).
                withTitle("car4").
                withUploadDate(date).
                withUploaderId(1).
                build();
        
        Printable p4 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car5").
                withRating(2).
                withNumberOfDownloads(18340).
                withId(9).
                withUploadDate(date).
                withUploaderId(1).
                withTitle("car5").
                build();
        
        indexables.add(p1);
        indexables.add(p2);
        indexables.add(p3);
        indexables.add(p4);
        
        for (Indexable p : indexables)
            Preconditions.checkNotNull(p);
        
        solrSearchManager.addToIndex(indexables);
    }
    
    @Test(expected = NullPointerException.class)
    public void indexNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Indexable idx = null;
        solrSearchManager.addToIndex(idx);
    }
    
    @Test(expected = NullPointerException.class)
    public void indexNullList() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<Indexable> indexableList = null;
        solrSearchManager.addToIndex(indexableList);
    }
    
    @AfterClass
    public static void clearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }

    //TODO addtoindex mit exception
}
