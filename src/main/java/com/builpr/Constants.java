package com.builpr;

import com.google.common.collect.Lists;

import java.util.List;

public class Constants {
    public static final String DATE_FORMAT = "yyyy-mm-dd";
    public static final String DATE_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";



    public static final String DATABASE_PASSWORD = "builpr123";


    public static final String BASE_URL = "http://localhost:8080";

    public static final String URL_LOGIN = "/login";
    public static final String URL_SIMPLEPAYLOAD = "/simplepayload";
    public static final String URL_GRAVATAR = "/gravatar";
    public static final String URL_REGISTER = "/register";
    public static final String URL_PROFILE = "/profile";
    public static final String URL_PROFILE_EDIT = "/profile/edit";
    public static final String URL_REDEEM_CONFIRMATION_TOKEN = "/redeem";
    public static final String URL_MESSAGE = "/message";



    public static final String SECURITY_ROLE_USER = "USER";
    public static final String SECURITY_CROSS_ORIGIN = "http://localhost:8081";
    public static final List<String> SECURITY_URLS = Lists.newArrayList(
            URL_SIMPLEPAYLOAD,
            URL_PROFILE_EDIT,
            URL_MESSAGE
    );



    public static final String USER_IMAGE_GRAVATAR_NOT_SET = "http://placehold.it/100x100";




    public static final String USERNAME_PATTERN = "[A-Za-z][A-Za-z0-9_]{3,}[A-Z-a-z0-9]";
    public static final int MIN_PASSWD_LENGTH = 6;



    public static final String SMTP_SERVER = "smtp.gmail.com";
    public static final int TLS_PORT = 587;
    public static final String CONFIRMATION_TOKEN_SENDER_ADDRESS = "juergen.bauer1233@gmail.com";
    public static final String CONFIRMATION_TOKEN_SENDER_PASSWD = "Juergen.Bauer123";
}

