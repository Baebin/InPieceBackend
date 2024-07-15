package com.piebin.inpiece.exception.dto;

import com.piebin.inpiece.exception.entity.AccountErrorCode;
import com.piebin.inpiece.exception.entity.PermissionErrorCode;
import com.piebin.inpiece.exception.entity.SystemErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorDto {
    private HttpStatus httpStatus;
    private String message;

    public ErrorDto(AccountErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    // Authority
    public ErrorDto(PermissionErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public ErrorDto(SystemErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}