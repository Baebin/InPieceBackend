package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.account.*;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AccountService {
    // Utility
    void register(AccountRegisterDto dto);
    AccountTokenDetailDto login(AccountLoginDto dto);

    // Getter
    AccountDetailDto loadProfile(SecurityAccount securityAccount);
    ResponseEntity<byte[]> loadProfileImage(SecurityAccount securityAccount) throws IOException;

    // Setter
    void editProfileImage(SecurityAccount securityAccount, MultipartFile file) throws IOException;
    void edit(SecurityAccount securityAccount, AccountEditDto dto);
    void editName(SecurityAccount securityAccount, AccountNameDto dto);
    void editPhone(SecurityAccount securityAccount, AccountPhoneDto dto);
    void editDescription(SecurityAccount securityAccount, AccountDescriptionDto dto);
    void editMajor(SecurityAccount securityAccount, AccountMajorDto dto);
    void editStudentId(SecurityAccount securityAccount, AccountStudentIdDto dto);

    // Deleter
    void deleteProfileImage(SecurityAccount securityAccount);
}
