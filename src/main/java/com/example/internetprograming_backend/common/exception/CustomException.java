package com.example.internetprograming_backend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public class CustomException extends RuntimeException{

    private final LocalDateTime timestamp;
    private final HttpStatusCode statusCode;
    private final String error;
    private final String message;
    private final CustomExceptionResponse customExceptionResponse;

    public CustomException(CustomExceptionResponse customExceptionResponse) {
        this.statusCode = customExceptionResponse.getHttpStatus();
        this.error = customExceptionResponse.getHttpStatus().name();
        this.message = customExceptionResponse.getMessage();
        this.timestamp = LocalDateTime.now();
        this.customExceptionResponse = customExceptionResponse;
    }

}
