package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * Provides the ability to do file-type-filtering
 */
public class FileFilter extends Filter {

    //TODO: eventuell mit einem String, falls nur nach einem Dateityp gefiltert werden soll?
    @Getter
    private List<String> fileTypes;

    /**
     * Creates a FileFilter-object
     *
     * @param fileTypes List of Strings representing file-types
     */
    public FileFilter(@NonNull List<String> fileTypes) {
        Preconditions.checkArgument(fileTypes.size() > 0);
        
        this.fileTypes = fileTypes;
    }
}
