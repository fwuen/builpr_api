package com.builpr.search.solr;

import com.builpr.search.model.PrintableReference;
import org.apache.solr.common.SolrDocument;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Provides test-methods for PrintableReferenceFactory-class
 */
public class PrintableReferenceFactoryTest {

    /**
     * Tries to get a List of PrintableReference-objects from the factory with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void getReferenceListWithNull() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List list = null;

        factory.get(list);
    }

    /**
     * Tries to get a single PrintableReference-object from the factory with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void getReferenceWithNull() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        SolrDocument document = null;

        factory.get(document);
    }

    /**
     * Tries to get a List of PrintableReference-objects from the factory with an empty List as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void getReferenceListWithEmptyList() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List list = Lists.newArrayList();

        factory.get(list);
    }

    /**
     * Gets a List of PrintableReference-objects from the factory with a legitimate List-object as parameter
     */
    @Test
    public void getReferenceListWithLegitDocumentList() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List<SolrDocument> documents = Lists.newArrayList();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, 1);
        documents.add(solrdoc);

        List<PrintableReference> references = factory.get(documents);

        Assert.assertNotNull(references);
        Assert.assertTrue(documents.size() == references.size());
    }

    /**
     * Gets a single PrintableReference-object from the factory with a legitimate SolrDocument-object as parameter
     */
    @Test
    public void getReferenceWithLegitDocument() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, 1);
        PrintableReference reference = factory.get(solrdoc);

        Assert.assertNotNull(reference);
    }


}
