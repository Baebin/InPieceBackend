package com.piebin.inpiece.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContestErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 대회 정보가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
