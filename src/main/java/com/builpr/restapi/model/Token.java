package com.builpr.restapi.model;

import lombok.Getter;

import java.util.UUID;

public class Token {
    @Getter
    private String token;
    
    public Token() {
        this.token = UUID.randomUUID().toString();
    }
}
