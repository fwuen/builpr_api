package com.builpr.search.solr;

import com.builpr.search.model.Indexable;
import lombok.NonNull;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.common.SolrInputDocument;

/**
 * Provides the ability to create Solr-specific input documents
 */
public class SolrInputDocumentFactory {

    /**
     * Creates and returns a SolrInputDocument-object
     * @param indexable Printable-object to be transformed into a SolrInputDocument-object
     * @return SolrInputDocument-object
     */
    public SolrInputDocument getInputDocumentWith(@NonNull Indexable indexable) {
        DocumentObjectBinder documentObjectBinder = new DocumentObjectBinder();

        return documentObjectBinder.toSolrInputDocument(indexable);
    }

    /*TODO: Wo wird das Boosting festgelegt? Bei SolrInputDocument und SolrInputField ist es laut Dokumentation Deprecated.*/
}
