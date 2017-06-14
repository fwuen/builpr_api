package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import jdk.nashorn.internal.parser.Token;
import lombok.Getter;

import java.util.Random;

//TODO: Unique-Constraint in Datenbank anlegen
//TODO: Sollte ein Token doppelt vorkommen, muss ein Fehler von der Datenbank zurückgegeben werden, der eine Neugenerierung des Tokens auslöst
public class TokenGenerator {

    private static final char[] ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-+!$%&/()={}?".toCharArray();
    private static final char[] ALLOWED_CHARS_NO_SPECIAL_CASES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    private static char[] USED_CHARS;

    public TokenGenerator() {
        USED_CHARS = ALLOWED_CHARS;
    }

    public TokenGenerator(int tokenSize) {
        USED_CHARS = ALLOWED_CHARS;
        setTokenSize(tokenSize);
    }

    public TokenGenerator(int tokenSize, boolean limited) {
        if (limited) {
            USED_CHARS = ALLOWED_CHARS_NO_SPECIAL_CASES;
        } else {
            USED_CHARS = ALLOWED_CHARS;
        }
        setTokenSize(tokenSize);
    }

    @Getter
    private int tokenSize = 128;

    private Random random = new Random();


    public void setTokenSize(int tokenSize) {
        Preconditions.checkArgument(tokenSize > 0);
        Preconditions.checkArgument(tokenSize < 512);

        this.tokenSize = tokenSize;
    }

    public String generate() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < tokenSize; i++)
            stringBuilder.append(getRandomChar());

        String token = stringBuilder.toString();

        Verify.verifyNotNull(token);
        Verify.verify(token.length() == tokenSize);

        return token;
    }

    private char getRandomChar() {
        return USED_CHARS[random.nextInt(USED_CHARS.length)];
    }

}