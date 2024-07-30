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

import java.util.List;

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
                contestService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/my")
    public ResponseEntity<List<ContestDetailDto>> loadAllMyContest(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                contestService.loadAllMyContest(securityAccount), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/rec_count")
    public ResponseEntity<List<ContestDetailDto>> loadAllWithMyRecCount(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                contestService.loadAllWithMyRecCount(securityAccount), HttpStatus.OK);
    }
}
