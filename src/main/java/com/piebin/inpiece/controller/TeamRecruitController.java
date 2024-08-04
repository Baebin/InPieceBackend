package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.team.TeamIdxDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitEditDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamRecruitService;
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
public class TeamRecruitController {
    private static final String API = "/api/team/recruit/";

    private final TeamRecruitService teamRecruitService;

    // Getter
    @GetMapping(API + "load")
    public ResponseEntity<TeamRecruitDetailDto> load(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamRecruitDto dto) {
        return new ResponseEntity<>(
                teamRecruitService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all")
    public ResponseEntity<List<TeamRecruitDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamIdxDto dto) {
        return new ResponseEntity<>(
                teamRecruitService.loadAll(securityAccount, dto), HttpStatus.OK);
    }

    // Setter
    @PostMapping(API + "update")
    public ResponseEntity<Boolean> update(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamRecruitEditDto dto) {
        teamRecruitService.update(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // File
    @GetMapping(API + "load/form")
    public ResponseEntity<byte[]> loadForm(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamRecruitDto dto) throws IOException {
        return teamRecruitService.loadForm(securityAccount, dto);
    }

    @PostMapping(API + "upload/form")
    public ResponseEntity<Boolean> uploadForm(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestPart(value = "file") MultipartFile file,
            @RequestPart(value = "dto") TeamRecruitDto dto) throws IOException {
        teamRecruitService.uploadForm(securityAccount, file, dto);
        return ResponseEntity.ok(true);
    }
}
