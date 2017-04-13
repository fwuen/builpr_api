package com.builpr.restapi.utils;

import java.util.UUID;

//TODO: Unique-Constraint in Datenbank anlegen
//TODO: Sollte ein Token doppelt vorkommen, muss ein Fehler von der Datenbank zurückgegeben werden, der eine Neugenerierung des Tokens auslöst
public class TokenGenerator {

    private TokenGenerator() { }

    public static String generate() {
        return UUID.randomUUID().toString();
    }

}
