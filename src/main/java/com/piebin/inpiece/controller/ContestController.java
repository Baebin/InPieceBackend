package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContestController {
    private static final String API = "/api/contest/";
    private static final Logger log = LoggerFactory.getLogger(ContestController.class);

    private final ContestService contestService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid ContestCreateDto dto) {
        contestService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Setter
    @PatchMapping(API + "edit/image")
    public ResponseEntity<Boolean> editProfileImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "dto") @Valid ContestIdxDto dto) throws IOException {
        contestService.editImage(securityAccount, file, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load/image")
    public ResponseEntity<byte[]> loadImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) throws IOException {
        return contestService.loadImage(securityAccount,dto);
    }

    @GetMapping(API + "load/detail")
    public ResponseEntity<ContestDetailDto> loadDetail(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) {
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
