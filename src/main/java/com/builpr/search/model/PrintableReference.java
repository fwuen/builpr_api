package com.builpr.search.model;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Provides the ability to use references to PrintModels
 * Primarily used to map Solr-requests to a Java-MVC-model
 */
public class PrintableReference {
    
    @Getter
    @Field
    public String id;
    
    /**
     * Creates a PrintableReference-object
     *
     * @param id ID of the PrintModel to be stored in the PrintableReference-object
     */
    public PrintableReference(String id) {
        Preconditions.checkArgument(Integer.parseInt(id) > 0);

        this.id = id;
    }

    public PrintableReference() {}
    
}
