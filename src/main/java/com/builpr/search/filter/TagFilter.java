package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

/**
 * Provides the ability to do tag-filtering
 */
public class TagFilter extends Filter{

    @Getter
    private List<String> tags;

    /**
     * Creates a TagFilter-object
     * @param tags
     */
    public TagFilter(@NonNull List<String> tags) {
        Preconditions.checkArgument(tags.size() > 0);

        this.tags = tags;
    }

}
