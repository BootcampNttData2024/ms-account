package com.vasquez.msaccount.service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int code;
    private String error;
    private String message;

    public ErrorResponse(int code, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
