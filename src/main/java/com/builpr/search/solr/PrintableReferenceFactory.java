package com.builpr.search.solr;

import com.builpr.search.model.PrintableReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * Provides the ability to create Solr-specific PrintableReference-objects
 */
public class PrintableReferenceFactory {

    /**
     * Creates and returns a List of PrintableReference-objects
     *
     * @param data List of SolrDocuments to be transformed and stored in PrintableReference-objects
     * @return List of PrintableReference-objects
     */
    public List<PrintableReference> get(@NonNull List<SolrDocument> data) {
        Preconditions.checkArgument(data.size() > 0);

        List<PrintableReference> results = Lists.newArrayList();

        for (SolrDocument solrDocument : data)
            results.add(get(solrDocument));

        return results;
    }

    /**
     * Creates and returns a single PrintableReference-object
     *
     * @param data SolrDocument to be transformed and stored in PrintableReference-object
     * @return PrintableReference-object
     */
    public PrintableReference get(@NonNull SolrDocument data) {
        Preconditions.checkArgument(data.containsKey(SolrFields.PRINT_MODEL_ID));

        DocumentObjectBinder documentObjectBinder = new DocumentObjectBinder();

        return documentObjectBinder.getBean(PrintableReference.class, data);
    }

}
