package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContestController {
    private static final String API = "/api/contest/";

    private final ContestService contestService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid ContestCreateDto dto) {
        contestService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
