package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author Dominic Fuchs
 * Abstract Database Manager. Every concrete Database Manager class should extend it
 *
 * @param <DaoType> the type of the Speedment manager the class is using
 */
public abstract class DatabaseManager<DaoType> {

    @Getter(AccessLevel.PROTECTED)
    private DaoType dao;

    /**
     * constructor that connects the class to the database
     *
     * @param clazz the class of the used Speedment manager
     */
    public DatabaseManager(@NonNull Class<DaoType> clazz) {
        this.dao = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(clazz);
    }

}
