package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import lombok.NonNull;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

    public static String getImageURLByEmail(@NonNull String email) {
        Preconditions.checkArgument(email.length() > 0);
        Preconditions.checkArgument(EmailValidator.getInstance().isValid(email));

        return "https://www.gravatar.com/avatar/" + md5Hex(email);
    }

    private static String md5Hex(@NonNull String toHex) {
        Preconditions.checkArgument(toHex.length() > 0);

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            return hex(messageDigest.digest(toHex.getBytes("CP1252")));
        } catch(NoSuchAlgorithmException ex) { }
        catch(UnsupportedEncodingException ex) { }

        return null; /* Cannot happen after all. */
    }

    private static String hex(@NonNull byte[] bytes) {
        Preconditions.checkArgument(bytes.length > 0);

        StringBuffer stringBuffer = new StringBuffer();

        for(int i = 0; i < bytes.length; i++) {
            stringBuffer.append(Integer.toHexString(
                    (bytes[i] & 0xFF) | 0x100
            ).substring(1, 3));
        }

        return stringBuffer.toString();
    }

}
