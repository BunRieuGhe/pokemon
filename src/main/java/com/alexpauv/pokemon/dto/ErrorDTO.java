package com.alexpauv.pokemon.dto;

import com.alexpauv.pokemon.exception.ErrorCode;

public class ErrorDTO {
    private ErrorCode code;

    public ErrorDTO(ErrorCode errorCode) {
        this.code = errorCode;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }
}
