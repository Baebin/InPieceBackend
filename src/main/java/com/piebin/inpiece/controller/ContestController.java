package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    // Getter
    @PostMapping(API + "load/detail")
    public ResponseEntity<ContestDetailDto> loadDetail(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid ContestIdxDto dto) {
        return new ResponseEntity<>(
                contestService.loadDetail(securityAccount, dto), HttpStatus.OK);
    }
}
