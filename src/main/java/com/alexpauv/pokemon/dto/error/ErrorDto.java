package com.alexpauv.pokemon.dto.error;

import com.alexpauv.pokemon.exception.ErrorCode;

import java.time.LocalDateTime;

public class ErrorDto {
    private ErrorCode code;

    private LocalDateTime timestamp;

    private String message;

    public ErrorDto(ErrorCode errorCode, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = errorCode;
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
