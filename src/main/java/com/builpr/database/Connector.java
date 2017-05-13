package com.builpr.database;

/**
 * Klasse, die eine DB-Connection herstellt. Im Standardfall wird das normale DB-Schema genutzt, man kann aber auch
 * ein anderes (z.B. für Tests angeben)
 */
public class Connector {

    private static String schemaName;

    /**
     * Diese Methode stellt eine Standardverbindung zur MySql-Datenbank her
     *
     * @return BuilprApplication
     */
    public static BuilprApplication getConnection() {
        if(schemaName == null) {
            return new BuilprApplicationBuilder().withPassword("builpr123").build();
        }else{
            return new BuilprApplicationBuilder().withSchema(schemaName).withPassword("builpr123").build();
        }

    }

    /**
     * Methode, um den Namen des benutzten Schemas zu ändern
     *
     * @param newSchemaName der Name des genutzten Schemas
     */
    public static void setSchemaName(String newSchemaName) {
        schemaName = newSchemaName;
    }
}
