package com.builpr.search.solr;

import com.builpr.search.model.IndexablePrintModel;
import lombok.NonNull;
import org.apache.solr.common.SolrInputDocument;

/**
 * Provides the ability to create Solr-specific input documents
 */
public class SolrInputDocumentFactory {

    /**
     * Creates and returns a SolrInputDocument-object
     * @param indexable IndexablePrintModel-object to be transformed into a SolrInputDocument-object
     * @return SolrInputDocument-object
     */
    public SolrInputDocument getInputDocumentWith(@NonNull IndexablePrintModel indexable) {
        SolrInputDocument inputDocument = new SolrInputDocument();

        inputDocument.addField(SolrFields.PRINT_MODEL_ID, indexable.getId());
        inputDocument.addField(SolrFields.PRINT_MODEL_TITLE, indexable.getTitle());
        inputDocument.addField(SolrFields.PRINT_MODEL_DESCRIPTION, indexable.getDescription());
        inputDocument.addField(SolrFields.PRINT_MODEL_TAGS, indexable.getTags());
        inputDocument.addField(SolrFields.PRINT_MODEL_TYPE, indexable.getType());
        inputDocument.addField(SolrFields.PRINT_MODEL_UPLOADER, indexable.getUploaderId());
        inputDocument.addField(SolrFields.PRINT_MODEL_UPLOAD_DATE, indexable.getUploadDate());
        inputDocument.addField(SolrFields.PRINT_MODEL_RATING, indexable.getRating());

        return inputDocument;
    }

    /*TODO: Wo wird das Boosting festgelegt? Bei SolrInputDocument und SolrInputField ist es laut Dokumentation Deprecated.*/
}
