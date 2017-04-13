package com.builpr.restapi.model;

import lombok.Getter;

import java.util.UUID;

//TODO: Unique-Constraint in Datenbank anlegen
//TODO: Sollte ein Token doppelt vorkommen, muss ein Fehler von der Datenbank zurückgegeben werden, der eine Neugenerierung des Tokens auslöst

public class Token {
    @Getter
    private String token;
    
    public Token() {
        this.token = UUID.randomUUID().toString();
    }
}
