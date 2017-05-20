package com.builpr.search.solr;

import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SolrInputDocumentFactoryTest {

    /**
     * Tries to get a SolrInputDocument-object from the factory with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void getInputDocumentWithNull() {
        SolrInputDocumentFactory factory = new SolrInputDocumentFactory();
        Indexable indexable = null;

        factory.getInputDocumentWith(indexable);
    }
    /**
     * Tries to get a SolrInputDocument-object from the factory with a legit Indexable-object as parameter
     * The test should be successful
     */
    @Test
    public void getInputDocumentWithLegitIndexable() {
        SolrInputDocumentFactory factory = new SolrInputDocumentFactory();
        Indexable indexable = new Printable.Builder()
                .withDescription("Test")
                .withId(1)
                .withRating(1.0)
                .withTags(new ArrayList<String>(Arrays.asList("Test")))
                .withTitle("Test")
                .withType("Test")
                .withUploadDate(new Date(0))
                .withUploaderId(1)
                .build();

         SolrInputDocument document = factory.getInputDocumentWith(indexable);

        Assert.assertNotNull(document);
    }
}
