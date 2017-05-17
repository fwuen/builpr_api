package com.builpr.search.solr;

import com.builpr.search.model.PrintModelReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.apache.solr.common.SolrDocument;

import java.util.List;

public class PrintModelReferenceFactory {

    /**
     *
     * @param data
     * @return
     */
    public List<PrintModelReference> get(@NonNull List<SolrDocument> data) {
        Preconditions.checkArgument(data.size() > 0);

        List<PrintModelReference> results = Lists.newArrayList();

        for(SolrDocument solrDocument : data)
            results.add(get(solrDocument));

        return results;
    }

    /**
     *
     * @param data
     * @return
     */
    public PrintModelReference get(@NonNull SolrDocument data) {
        Preconditions.checkArgument(data.containsKey(SolrFields.PRINT_MODEL_ID));

        return new PrintModelReference(
            Integer.valueOf(String.valueOf(data.get(SolrFields.PRINT_MODEL_ID)))
        );
    }

}
