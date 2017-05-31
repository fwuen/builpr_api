package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * Provides the ability to do tag-filtering
 */
public class CategoryFilter extends Filter {
    
    @Getter
    private List<String> categories;
    
    /**
     * Creates a CategoryFilter-object
     *
     * @param categories List of Strings representing the categories
     */
    public CategoryFilter(@NonNull List<String> categories) {
        Preconditions.checkArgument(categories.size() > 0);
        
        this.categories = categories;
    }
    
}
