package com.builpr.restapi.model.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * basic response
 */
public class Response<responseType, errorType> {

    @Setter
    @Getter
    private boolean success = true;

    @Setter
    @Getter
    private responseType payload;

    @Setter
    @Getter
    private List<errorType> errorList;

    public void addError(errorType error) {
        errorList.add(error);
    }

}
