package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.team.TeamCreateDto;
import com.piebin.inpiece.model.dto.team.TeamIdxDto;
import com.piebin.inpiece.model.dto.team.TeamMemberDto;
import com.piebin.inpiece.model.dto.team_member.TeamMemberDetailDto;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private static final String API = "/api/team/";

    private final TeamService teamService;

    // Utility
    @PostMapping(API + "create")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamCreateDto dto) {
        teamService.create(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "delete")
    public ResponseEntity<Boolean> create(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamIdxDto dto) {
        teamService.delete(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @PostMapping(API + "invite")
    public ResponseEntity<Boolean> invite(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamMemberDto dto) {
        teamService.invite(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "remove")
    public ResponseEntity<Boolean> remove(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamMemberDto dto) {
        teamService.remove(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Getter
    @GetMapping(API + "load/all")
    public ResponseEntity<List<TeamMemberDetailDto>> loadAll(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                teamService.loadAll(securityAccount), HttpStatus.OK);
    }
}
