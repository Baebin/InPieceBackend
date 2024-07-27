package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.AccountException;
import com.piebin.inpiece.exception.entity.AccountErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.dto.account.AccountDetailDto;
import com.piebin.inpiece.model.dto.account.AccountLoginDto;
import com.piebin.inpiece.model.dto.account.AccountRegisterDto;
import com.piebin.inpiece.model.dto.account.AccountTokenDetailDto;
import com.piebin.inpiece.repository.AccountRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.security.TokenProvider;
import com.piebin.inpiece.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional(readOnly = true)
    public AccountDetailDto loadProfile(SecurityAccount securityAccount) {
        return AccountDetailDto.toDto(securityAccount.getAccount());
    }

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
}
