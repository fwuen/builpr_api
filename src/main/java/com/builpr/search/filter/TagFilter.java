package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class TagFilter extends Filter{

    @Getter
    private List<String> tags;

    /**
     *
     * @param tags
     */
    public TagFilter(@NonNull List<String> tags) {
        Preconditions.checkArgument(tags.size() > 0);

        this.tags = tags;
    }

}
