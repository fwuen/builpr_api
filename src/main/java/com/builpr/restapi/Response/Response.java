package com.builpr.restapi.Response;

/**
 * Generic response class
 *
 * @param <PayloadType>
 */
public class Response<PayloadType> {
    boolean success;
    Error error;
    PayloadType payload;
}
