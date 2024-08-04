package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.team_project.TeamProjectCreateDto;
import com.piebin.inpiece.model.dto.team_project.TeamProjectIdxDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
