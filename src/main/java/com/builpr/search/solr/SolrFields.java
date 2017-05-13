package com.builpr.search.solr;

import java.util.List;

public class SolrFields {

    public static final String PRINT_MODEL_ID = "id";
    public static final float BOOST_MODEL_ID = 0.0f;

    public static final String PRINT_MODEL_TITLE = "title";
    public static final float BOOST_MODEL_TITLE = 2.0f;

    public static final String PRINT_MODEL_DESCRIPTION = "description";
    public static final float BOOST_MODEL_DESCRIPTION = 1.0f;

    //TODO: alle Tags in einem Solr-Field gespeichert?
    public static final String PRINT_MODEL_TAGS = "tags";
    public static final float BOOST_MODEL_TAGS = 2.0f;

    public static final String PRINT_MODEL_AGE_RESTRICTION = "age_restriction";
    public static final float BOOST_MODEL_AGE_RESTRICTION = 0.0f;

    public static final String PRINT_MODEL_UPLOADER = "uploader";
    public static final float BOOST_MODEL_UPLOADER = 0.0f;

    public static final String PRINT_MODEL_UPLOAD_DATE = "upload_date";
    public static final float BOOST_MODEL_UPLOAD_DATE = 0.0f;

    public static final String PRINT_MODEL_RATING = "rating";
    public static final float BOOST_MODEL_RATING = 1.5f;



}
