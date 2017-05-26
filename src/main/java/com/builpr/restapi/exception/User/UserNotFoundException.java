package com.builpr.restapi.exception.User;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no user found")
public class UserNotFoundException extends RuntimeException {

}
