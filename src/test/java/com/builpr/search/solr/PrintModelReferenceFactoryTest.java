package com.builpr.search.solr;

import com.builpr.search.model.PrintModelReference;
import org.apache.solr.common.SolrDocument;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Provides test-methods for PrintModelReferenceFactory-class
 */
public class PrintModelReferenceFactoryTest {

    /**
     * Tries to get a List of PrintModelReference-objects from the factory with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void getReferenceListWithNull() {
        PrintModelReferenceFactory factory = new PrintModelReferenceFactory();
        List list = null;

        factory.get(list);
    }

    /**
     * Tries to get a single PrintModelReference-object from the factory with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void getReferenceWithNull() {
        PrintModelReferenceFactory factory = new PrintModelReferenceFactory();
        SolrDocument document = null;

        factory.get(document);
    }

    /**
     * Tries to get a List of PrintModelReference-objects from the factory with an empty List as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void getReferenceListWithEmptyList() {
        PrintModelReferenceFactory factory = new PrintModelReferenceFactory();
        List list = Lists.newArrayList();

        factory.get(list);
    }

    /**
     * Gets a List of PrintModelReference-objects from the factory with a legitimate List-object as parameter
     */
    @Test
    public void getReferenceListWithLegitDocumentList() {
        PrintModelReferenceFactory factory = new PrintModelReferenceFactory();
        List<SolrDocument> documents = Lists.newArrayList();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, 1);
        documents.add(solrdoc);

        List<PrintModelReference> references = factory.get(documents);

        Assert.assertNotNull(references);
        Assert.assertTrue(documents.size() == references.size());
    }

    /**
     * Gets a single PrintModelReference-object from the factory with a legitimate SolrDocument-object as parameter
     */
    @Test
    public void getReferenceWithLegitDocument() {
        PrintModelReferenceFactory factory = new PrintModelReferenceFactory();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, 1);
        PrintModelReference reference = factory.get(solrdoc);

        Assert.assertNotNull(reference);
    }


}
