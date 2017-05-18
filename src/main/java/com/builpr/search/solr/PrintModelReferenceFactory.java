package com.builpr.search.solr;

import com.builpr.search.model.PrintModelReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.common.SolrDocument;

import java.util.List;

/**
 * Provides the ability to create Solr-specific PrintModelReference-objects
 */
public class PrintModelReferenceFactory {

    /**
     * Creates and returns a List of PrintModelReference-objects
     *
     * @param data List of SolrDocuments to be transformed and stored in PrintModelReference-objects
     * @return List of PrintModelReference-objects
     */
    public List<PrintModelReference> get(@NonNull List<SolrDocument> data) {
        Preconditions.checkArgument(data.size() > 0);

        List<PrintModelReference> results = Lists.newArrayList();

        for (SolrDocument solrDocument : data)
            results.add(get(solrDocument));

        return results;
    }

    /**
     * Creates and returns a single PrintModelReference-object
     *
     * @param data SolrDocument to be transformed and stored in PrintModelReference-object
     * @return PrintModelReference-object
     */
    public PrintModelReference get(@NonNull SolrDocument data) {
        Preconditions.checkArgument(data.containsKey(SolrFields.PRINT_MODEL_ID));

        return new PrintModelReference(
                Integer.valueOf(String.valueOf(data.get(SolrFields.PRINT_MODEL_ID)))
        );
    }

}
