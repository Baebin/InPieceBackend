package com.piebin.inpiece.exception;

import com.piebin.inpiece.exception.entity.PermissionErrorCode;
import lombok.Getter;

@Getter
public class PermissionException extends RuntimeException {
    private PermissionErrorCode errorCode;

    public PermissionException(PermissionErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
