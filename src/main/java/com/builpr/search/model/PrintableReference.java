package com.builpr.search.model;

import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 * Provides the ability to use references to PrintModels
 * Primarily used to map Solr-requests to a Java-MVC-model
 */
public class PrintableReference {
    
    @Getter
    private int id;
    
    /**
     * Creates a PrintableReference-object
     *
     * @param id ID of the PrintModel to be stored in the PrintableReference-object
     */
    public PrintableReference(int id) {
        Preconditions.checkArgument(id > 0);
        
        this.id = id;
    }

    public PrintableReference() {}
    
}
