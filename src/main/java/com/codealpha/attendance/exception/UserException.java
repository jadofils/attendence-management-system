package com.codealpha.attendance.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public UserException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static UserException badRequest(String message) {
        return new UserException(HttpStatus.BAD_REQUEST, message);
    }

    public static UserException notFound(String message) {
        return new UserException(HttpStatus.NOT_FOUND, message);
    }

    public static UserException internalServerError(String message) {
        return new UserException(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static UserException success(String message) {
        return new UserException(HttpStatus.OK, message);
    }
}
