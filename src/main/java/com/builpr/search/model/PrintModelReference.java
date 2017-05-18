package com.builpr.search.model;

import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 * Provides the ability to use references to PrintModels
 * Primarily used to map Solr-requests to a Java-MVC-model
 */
public class PrintModelReference {
    
    @Getter
    private int id;
    
    /**
     * Creates a PrintModelReference-object
     *
     * @param id ID of the PrintModel to be stored in the PrintModelReference-object
     */
    public PrintModelReference(int id) {
        Preconditions.checkArgument(id > 0);
        
        this.id = id;
    }
    
}
