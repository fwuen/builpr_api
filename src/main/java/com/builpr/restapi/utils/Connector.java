package com.builpr.restapi.utils;


import com.builpr.database.BuilprApplication;
import com.builpr.database.BuilprApplicationBuilder;


public class Connector {

    private static String schemaName;

    public static BuilprApplication getConnection() {
        if(schemaName == null) {
            return new BuilprApplicationBuilder().withPassword("builpr123").build();
        }else{
            return new BuilprApplicationBuilder().withSchema(schemaName).withPassword("builpr123").build();
        }

    }

    public static void setSchemaName(String newSchemaName) {
        schemaName = newSchemaName;
    }

}
