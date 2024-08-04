package com.piebin.inpiece.controller;

import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.team.*;
import com.piebin.inpiece.model.dto.team.TeamDetailDto;
import com.piebin.inpiece.model.dto.team_member.TeamMemberDto;
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
    public ResponseEntity<Boolean> delete(
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
    @GetMapping(API + "load")
    public ResponseEntity<TeamDetailDto> load(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamIdxDto dto) {
        return new ResponseEntity<>(
                teamService.load(securityAccount, dto), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/my")
    public ResponseEntity<List<TeamDetailDto>> loadAllMyTeam(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                teamService.loadAllMyTeam(securityAccount), HttpStatus.OK);
    }

    @GetMapping(API + "load/all/my/own")
    public ResponseEntity<List<TeamDetailDto>> loadAllMyOwnTeam(
            @AuthenticationPrincipal SecurityAccount securityAccount) {
        return new ResponseEntity<>(
                teamService.loadAllMyOwnTeam(securityAccount), HttpStatus.OK);
    }

    // Setter
    @PatchMapping(API + "edit/name")
    public ResponseEntity<Boolean> editName(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamNameDto dto) {
        teamService.editName(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    // Contest
    @PostMapping(API + "add/contest")
    public ResponseEntity<Boolean> addContest(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamContestDto dto) {
        teamService.addContest(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(API + "remove/contest")
    public ResponseEntity<Boolean> removeContest(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @RequestBody @Valid TeamContestDto dto) {
        teamService.removeContest(securityAccount, dto);
        return ResponseEntity.ok(true);
    }

    @GetMapping(API + "load/all/contest")
    public ResponseEntity<List<ContestDetailDto>> loadAllContest(
            @AuthenticationPrincipal SecurityAccount securityAccount,
            @Valid TeamIdxDto dto) {
        return new ResponseEntity<>(
                teamService.loadAllContest(securityAccount, dto), HttpStatus.OK);
    }
}
