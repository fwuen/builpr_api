package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.List;

public class FileFilter extends Filter {
    
    @Getter
    private List<String> fileTypes;
    
    public FileFilter(List<String> fileTypes) {
        Preconditions.checkArgument(fileTypes.size() > 0);
        
        this.fileTypes = fileTypes;
    }
}
