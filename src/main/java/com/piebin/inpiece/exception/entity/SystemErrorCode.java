package com.piebin.inpiece.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SystemErrorCode {
    UNKNOWN(HttpStatus.BAD_REQUEST, "알 수 없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
