package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import lombok.Getter;

import java.util.UUID;

//TODO: Unique-Constraint in Datenbank anlegen
//TODO: Sollte ein Token doppelt vorkommen, muss ein Fehler von der Datenbank zurückgegeben werden, der eine Neugenerierung des Tokens auslöst
public class TokenGenerator {

    @Getter
    private int tokenSize = 128;
    public void setTokenSize(int tokenSize) {
        Preconditions.checkArgument(tokenSize > 0);

        this.tokenSize = tokenSize;
    }



    public String generate() {
        String token = UUID.randomUUID().toString();

        Verify.verifyNotNull(token);
        Verify.verify(token.length() == tokenSize);

        return token;
    }

}