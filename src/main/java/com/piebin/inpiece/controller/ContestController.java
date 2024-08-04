package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.contest.*;
import com.piebin.inpiece.model.dto.team.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team_member.TeamDetailDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @GetMapping(API + "load/image")
    public ResponseEntity<byte[]> loadImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) throws IOException {
        return contestService.loadImage(securityAccount,dto);
    }

    @GetMapping(API + "load/detail")
    public ResponseEntity<ContestDetailDto> load(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) {
        return new ResponseEntity<>(
                contestService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all")
    public ResponseEntity<List<ContestDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestParam(value = "filter", defaultValue = "reg_date") String filter,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "12") int count) {
        return new ResponseEntity<>(
                contestService.loadAll(securityAccount, filter, sort, page, count), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/my")
    public ResponseEntity<List<ContestDetailDto>> loadAllMyContest(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                contestService.loadAllMyContest(securityAccount), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/team")
    public ResponseEntity<List<TeamDetailDto>> loadAllTeam(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) {
        return new ResponseEntity<>(
                contestService.loadAllTeam(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/recruit")
    public ResponseEntity<List<TeamRecruitDetailDto>> loadAllRecruit(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid ContestIdxDto dto) {
        return new ResponseEntity<>(
                contestService.loadAllRecruit(securityAccount, dto), HttpStatus.OK);
    }

    // Setter
    @PutMapping(API + "edit")
    public ResponseEntity<Boolean> edit(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid ContestEditDto dto) throws IOException {
        contestService.edit(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/image")
    public ResponseEntity<Boolean> editProfileImage(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "dto") @Valid ContestIdxDto dto) throws IOException {
        contestService.editImage(securityAccount, file, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/recommend")
    public ResponseEntity<Boolean> editRecommend(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid ContestRecommendDto dto) {
        contestService.editRecommend(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
