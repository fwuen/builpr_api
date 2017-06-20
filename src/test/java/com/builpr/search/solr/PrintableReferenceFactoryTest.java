package com.builpr.search.solr;

import com.builpr.search.model.PrintableReference;
import org.apache.solr.common.SolrDocument;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Felix Wünsche
 */
public class PrintableReferenceFactoryTest {

    @Test(expected = NullPointerException.class)
    public void getReferenceListWithNull() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List<SolrDocument> list = null;
    
        factory.get(list);
    }
    
    @Test(expected = NullPointerException.class)
    public void getReferenceWithNull() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        SolrDocument document = null;

        factory.get(document);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getReferenceListWithEmptyList() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List<SolrDocument> list = Lists.newArrayList();

        factory.get(list);
    }

    @Test
    public void getReferenceListWithLegitDocumentList() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        List<SolrDocument> documents = Lists.newArrayList();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, "1");
        documents.add(solrdoc);

        List<PrintableReference> references = factory.get(documents);

        Assert.assertNotNull(references);
        Assert.assertTrue(documents.size() == references.size());
    }

    @Test
    public void getReferenceWithLegitDocument() {
        PrintableReferenceFactory factory = new PrintableReferenceFactory();
        SolrDocument solrdoc = new SolrDocument();

        solrdoc.put(SolrFields.PRINT_MODEL_ID, "1");
        PrintableReference reference = factory.get(solrdoc);

        Assert.assertNotNull(reference);
    }


}
