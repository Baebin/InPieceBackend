package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.account.*;
import com.piebin.inpiece.security.SecurityAccount;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountTokenDetailDto login(AccountLoginDto dto);

    // Getter
    AccountDetailDto loadProfile(SecurityAccount securityAccount);

    // Setter
    void editName(SecurityAccount securityAccount, AccountNameDto dto);
    void editDescription(SecurityAccount securityAccount, AccountDescriptionDto dto);
    void editMajor(SecurityAccount securityAccount, AccountMajorDto dto);
    void editStudentId(SecurityAccount securityAccount, AccountStudentIdDto dto);
}
