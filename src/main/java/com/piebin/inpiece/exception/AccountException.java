package com.piebin.inpiece.exception;

import com.piebin.inpiece.exception.entity.AccountErrorCode;
import lombok.Getter;

@Getter
public class AccountException extends RuntimeException {
    private AccountErrorCode errorCode;

    public AccountException(AccountErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
