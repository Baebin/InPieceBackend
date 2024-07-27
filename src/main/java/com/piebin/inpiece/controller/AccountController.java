package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.account.AccountDetailDto;
import com.piebin.inpiece.model.dto.account.AccountLoginDto;
import com.piebin.inpiece.model.dto.account.AccountRegisterDto;
import com.piebin.inpiece.model.dto.account.AccountTokenDetailDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private static final String API = "/api/account/";

    private final AccountService accountService;

    // Getter
    @PostMapping(API + "load/profile")
    public ResponseEntity<AccountDetailDto> loadProfile(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                accountService.loadProfile(securityAccount), HttpStatus.OK);
    }

    // Utility
    @PostMapping(API + "register")
    public ResponseEntity<Boolean> register(
            @RequestBody @Valid AccountRegisterDto dto) {
        accountService.register(dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(API + "login")
    public ResponseEntity<AccountTokenDetailDto> login(
            @RequestBody @Valid AccountLoginDto dto) {
        return new ResponseEntity<>(
                accountService.login(dto), HttpStatus.OK);
    }
}
