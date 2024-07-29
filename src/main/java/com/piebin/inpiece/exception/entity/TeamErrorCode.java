package com.piebin.inpiece.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TeamErrorCode {
    NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 팀 정보가 없습니다."),
    IS_OWNER(HttpStatus.CONFLICT, "팀 소유주입니다."),
    IS_MEMBER(HttpStatus.CONFLICT, "이미 팀 멤버입니다."),
    IS_NON_OWNER(HttpStatus.BAD_REQUEST, "팀 소유주가 아닙니다."),
    IS_NON_MEMBER(HttpStatus.BAD_REQUEST, "팀 멤버가 아닙니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
