package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.AccountException;
import com.piebin.inpiece.exception.entity.AccountErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.dto.account.*;
import com.piebin.inpiece.model.dto.image.ImageDetailDto;
import com.piebin.inpiece.model.dto.image.ImageDto;
import com.piebin.inpiece.repository.AccountRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.security.TokenProvider;
import com.piebin.inpiece.service.AccountService;
import com.piebin.inpiece.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // Utility
    @Override
    @Transactional
    public void register(AccountRegisterDto dto) {
        if (accountRepository.existsById(dto.getId()))
            throw new AccountException(AccountErrorCode.ID_DUPLICATED);
        Account account = Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountTokenDetailDto login(AccountLoginDto dto) {
        Account account = accountRepository.findById(dto.getId())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        if (!passwordEncoder.matches(dto.getPassword(), account.getPassword()))
            throw new AccountException(AccountErrorCode.PASSWORD_INCORRECT);
        String token = tokenProvider.createAccessToken(dto.getId());
        return AccountTokenDetailDto.builder()
                .token(token)
                .build();
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public AccountDetailDto loadProfile(SecurityAccount securityAccount) {
        return AccountDetailDto.toDto(securityAccount.getAccount());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadProfileImage(SecurityAccount securityAccount) throws IOException {
        String path = "user/" + securityAccount.getAccount().getMajor();
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Setter
    @Override
    @Transactional
    public void editProfileImage(SecurityAccount securityAccount, MultipartFile file) throws IOException {
        String path = "user/" + securityAccount.getAccount().getMajor();
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional
    public void editName(SecurityAccount securityAccount, AccountNameDto dto) {
        Account account = accountRepository.findByIdx(securityAccount.getAccount().getIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setName(dto.getName());
    }

    @Override
    @Transactional
    public void editDescription(SecurityAccount securityAccount, AccountDescriptionDto dto) {
        Account account = accountRepository.findByIdx(securityAccount.getAccount().getIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setDescription(dto.getDescription());
    }

    @Override
    @Transactional
    public void editMajor(SecurityAccount securityAccount, AccountMajorDto dto) {
        Account account = accountRepository.findByIdx(securityAccount.getAccount().getIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setMajor(dto.getMajor());
    }

    @Override
    @Transactional
    public void editStudentId(SecurityAccount securityAccount, AccountStudentIdDto dto) {
        Account account = accountRepository.findByIdx(securityAccount.getAccount().getIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        account.setStudentId(dto.getStudentId());
    }

    // Deleter
    @Override
    @Transactional
    public void deleteProfileImage(SecurityAccount securityAccount) {
        String path = "user/" + securityAccount.getAccount().getMajor();
        String name = "profile";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.delete(imageDto);
    }
}
