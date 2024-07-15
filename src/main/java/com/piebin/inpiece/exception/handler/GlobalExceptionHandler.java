package com.piebin.inpiece.exception.handler;

import com.piebin.inpiece.exception.AccountException;
import com.piebin.inpiece.exception.PermissionException;
import com.piebin.inpiece.exception.SystemException;
import com.piebin.inpiece.exception.dto.ErrorDto;
import com.piebin.inpiece.exception.entity.PermissionErrorCode;
import com.piebin.inpiece.exception.entity.SystemErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccountException.class)
    public ResponseEntity<?> handleAccountException(AccountException e) {
        ErrorDto response = new ErrorDto(e.getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // Authority
    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<?> handlePermissionException(PermissionException e) {
        ErrorDto response = new ErrorDto(e.getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e) {
        ErrorDto response = new ErrorDto(SystemErrorCode.UNKNOWN);
        if (!ObjectUtils.isEmpty(e.getMessage()))
            if (e.getMessage().contains("Account"))
                response = new ErrorDto(PermissionErrorCode.FORBIDDEN);
        log.error(e.getMessage());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // System
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<?> handleSystemException(SystemException e) {
        ErrorDto response = new ErrorDto(e.getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}