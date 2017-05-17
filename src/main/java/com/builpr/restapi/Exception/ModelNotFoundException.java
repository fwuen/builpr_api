package com.builpr.restapi.Exception;

import com.builpr.database.db.builpr.model.Model;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException() {
        super();
    }

    public ModelNotFoundException(String message) {
        super(message);
    }
}
