package com.builpr.restapi.exception;

import com.builpr.database.db.builpr.model.Model;

public class ModelNotFoundException extends Exception {

    public ModelNotFoundException() {
        super();
    }

    public ModelNotFoundException(String message) {
        super(message);
    }
}
