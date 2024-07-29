package com.piebin.inpiece.exception;

import com.piebin.inpiece.exception.entity.TeamErrorCode;
import lombok.Getter;

@Getter
public class TeamException extends RuntimeException {
    private TeamErrorCode errorCode;

    public TeamException(TeamErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
