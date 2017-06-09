package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

public abstract class DatabaseManager<DaoType> {

    @Getter(AccessLevel.PROTECTED)
    private DaoType dao;

    public DatabaseManager(@NonNull Class<DaoType> clazz) {
        this.dao = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(clazz);
    }

}
