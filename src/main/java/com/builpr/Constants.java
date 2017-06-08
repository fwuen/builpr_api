package com.builpr;

import com.google.common.collect.Lists;

import java.util.List;

public class Constants {
    public static final String DATE_FORMAT = "yyyy-mm-dd";



    public static final String DATABASE_PASSWORD = "builpr123";



    public static final String ROLE_USER = "USER";



    public static final String SECURITY_CROSS_ORIGIN = "http://localhost:8081";
    public static final String SECURITY_USER = "hasRole('" + ROLE_USER + "')";



    public static final String URL_LOGIN = "/login";
    public static final String URL_SIMPLEPAYLOAD = "/simplepayload";
    public static final String URL_GRAVATAR = "/gravatar";

    public static final List<String> SECURED_URLS = Lists.newArrayList(URL_SIMPLEPAYLOAD);



    public static final String USER_IMAGE_GRAVATAR_NOT_SET = "http://placehold.it/100x100";
}

