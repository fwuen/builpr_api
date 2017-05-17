package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.List;

/**
 * Provides the ability to do file-type-filtering
 * @author
 * @author
 */
public class FileFilter extends Filter {
    
    @Getter
    private List<String> fileTypes;

    /**
     * Creates a FileFilter-object
     * @param fileTypes List of Strings representing file-types
     */
    public FileFilter(List<String> fileTypes) {
        Preconditions.checkArgument(fileTypes.size() > 0);
        
        this.fileTypes = fileTypes;
    }
}
