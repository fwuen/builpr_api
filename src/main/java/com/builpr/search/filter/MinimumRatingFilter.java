package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;

public class MinimumRatingFilter extends Filter {

    public static final int LOWEST_POSSIBLE_RATING = 1;
    public static final int HIGHEST_POSSIBLE_RATING = 5;

    @Getter
    private int minimumRating;

    public MinimumRatingFilter(int minimumRating) {
        Preconditions.checkArgument(minimumRating >= 0);
        Preconditions.checkArgument(minimumRating >= LOWEST_POSSIBLE_RATING);
        Preconditions.checkArgument(minimumRating <= HIGHEST_POSSIBLE_RATING);
        Preconditions.checkArgument(LOWEST_POSSIBLE_RATING < HIGHEST_POSSIBLE_RATING);

        this.minimumRating = minimumRating;
    }

}
