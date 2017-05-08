package com.builpr.search.filter;

import com.google.common.base.Preconditions;
import lombok.Getter;

public class PriceRangeFilter extends Filter {

    @Getter
    private int fromPrice;

    @Getter
    private int toPrice;

    public PriceRangeFilter(int fromPrice, int toPrice) {
        Preconditions.checkArgument(fromPrice >= 0);
        Preconditions.checkArgument(toPrice > fromPrice);

        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
    }

}
