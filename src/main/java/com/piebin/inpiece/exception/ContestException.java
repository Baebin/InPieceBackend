package com.piebin.inpiece.exception;

import com.piebin.inpiece.exception.entity.ContestErrorCode;
import lombok.Getter;

@Getter
public class ContestException extends RuntimeException {
    private ContestErrorCode errorCode;

    public ContestException(ContestErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
