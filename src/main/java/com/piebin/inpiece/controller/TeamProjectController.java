package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.team_project.*;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamProjectController {
    private static final String API = "/api/team/project/";

    private final TeamProjectService teamProjectService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamProjectCreateDto dto) {
        teamProjectService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "delete")
    public ResponseEntity<Boolean> delete(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamProjectIdxDto dto) {
        teamProjectService.delete(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load")
    public ResponseEntity<TeamProjectDetailDto> load(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamProjectIdxDto dto) {
        return new ResponseEntity<>(
                teamProjectService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all")
    public ResponseEntity<List<TeamProjectDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestParam(value = "filter", defaultValue = "reg_date") String filter,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "count", defaultValue = "12") int count) {
        return new ResponseEntity<>(
                teamProjectService.loadAll(securityAccount, filter, sort, page, count), HttpStatus.OK);
    }

    // Setter
    @PutMapping(API + "edit")
    public ResponseEntity<Boolean> edit(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamProjectEditDto dto) {
        teamProjectService.edit(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PatchMapping(API + "edit/recommend")
    public ResponseEntity<Boolean> editRecommend(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamProjectRecommendDto dto) {
        teamProjectService.editRecommend(securityAccount, dto);
        return ResponseEntity.ok(true);
    }
}
