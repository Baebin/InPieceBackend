package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.account.*;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private static final String API = "/api/account/";

    private final AccountService accountService;

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

    // Getter
    @GetMapping(API + "load/profile")
    public ResponseEntity<AccountDetailDto> loadProfile(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                accountService.loadProfile(securityAccount), HttpStatus.OK);
    }

    // Setter
    @PatchMapping(API + "edit/name")
    public ResponseEntity<Boolean> editName(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountNameDto dto) {
        accountService.editName(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/description")
    public ResponseEntity<Boolean> editDescription(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountDescriptionDto dto) {
        accountService.editDescription(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/major")
    public ResponseEntity<Boolean> editMajor(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountMajorDto dto) {
        accountService.editMajor(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/student_id")
    public ResponseEntity<Boolean> editStudentId(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid AccountStudentIdDto dto) {
        accountService.editStudentId(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
