package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.account.AccountDetailDto;
import com.piebin.inpiece.model.dto.account.AccountLoginDto;
import com.piebin.inpiece.model.dto.account.AccountRegisterDto;
import com.piebin.inpiece.model.dto.account.AccountTokenDetailDto;
import com.piebin.inpiece.security.SecurityAccount;

public interface AccountService {
    AccountDetailDto getAccount(SecurityAccount securityAccount);

    void register(AccountRegisterDto dto);

    AccountTokenDetailDto login(AccountLoginDto dto);
}
