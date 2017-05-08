package com.builpr.search.model;

import com.google.common.base.Preconditions;
import lombok.Getter;

public class PrintModelReference {

    @Getter
    private int id;

    public PrintModelReference(int id) {
        Preconditions.checkArgument(id > 0);

        this.id = id;
    }

}
