package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.AccountException;
import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.SystemException;
import com.piebin.inpiece.exception.TeamException;
import com.piebin.inpiece.exception.entity.AccountErrorCode;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.exception.entity.SystemErrorCode;
import com.piebin.inpiece.exception.entity.TeamErrorCode;
import com.piebin.inpiece.model.domain.*;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.team.*;
import com.piebin.inpiece.model.dto.team_member.TeamDetailDto;
import com.piebin.inpiece.model.dto.team_member.TeamMemberDetailDto;
import com.piebin.inpiece.repository.*;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final AccountRepository accountRepository;
    private final ContestRepository contestRepository;
    private final ContestTeamRepository contestTeamRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamRecruitRepository teamRecruitRepository;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, TeamCreateDto dto) {
        Account account = securityAccount.getAccount();
        Team team = Team.builder()
                .name(dto.getName())
                .owner(account)
                .build();
        teamRepository.save(team);

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .account(account)
                .build();
        teamMemberRepository.save(teamMember);
    }

    @Override
    @Transactional
    public void delete(SecurityAccount securityAccount, TeamIdxDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);
        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public void invite(SecurityAccount securityAccount, TeamMemberDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);
        Account eAccount = accountRepository.findByIdx(dto.getAccountIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        if (team.isMember(eAccount))
            throw new TeamException(TeamErrorCode.IS_MEMBER);
        TeamMember teamMember = TeamMember.builder()
                .account(eAccount)
                .team(team)
                .build();
        teamMemberRepository.save(teamMember);
    }

    @Override
    @Transactional
    public void remove(SecurityAccount securityAccount, TeamMemberDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        Account eAccount = accountRepository.findByIdx(dto.getAccountIdx())
                .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND));
        if (account.getIdx().equals(eAccount.getIdx())) {
            if (team.isOwner(account))
                throw new TeamException(TeamErrorCode.IS_OWNER);
        } else {
            if (!team.isOwner(account))
                throw new TeamException(TeamErrorCode.IS_NON_OWNER);
        }
        Optional<TeamMember> optionalTeamMember = team.getMember(eAccount);
        if (optionalTeamMember.isEmpty())
            throw new TeamException(TeamErrorCode.IS_NON_MEMBER);
        TeamMember teamMember = optionalTeamMember.get();
        teamMemberRepository.delete(teamMember);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public TeamDetailDto load(SecurityAccount securityAccount, TeamIdxDto dto) {
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        return TeamDetailDto.toDto(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberDetailDto> loadAllMyTeam(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();

        List<TeamMemberDetailDto> dtos = new ArrayList<>();
        for (TeamMember teamMember : teamMemberRepository.findAllByAccountOrderByIdxDesc(account))
            dtos.add(TeamMemberDetailDto.toDto(teamMember));
        return dtos;
    }

    // Setter
    @Override
    @Transactional
    public void editName(SecurityAccount securityAccount, TeamNameDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);
        team.setName(dto.getName());
    }

    // Contest
    @Override
    @Transactional
    public void addContest(SecurityAccount securityAccount, TeamContestDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        if (team.containsContest(contest))
            throw new ContestException(ContestErrorCode.EXISTS);

        TeamContest teamContest = TeamContest.builder()
                .contest(contest)
                .team(team)
                .build();
        contestTeamRepository.save(teamContest);
    }

    @Override
    @Transactional
    public void removeContest(SecurityAccount securityAccount, TeamContestDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        Optional<TeamContest> teamContest = team.getTeamContest(contest);
        if (teamContest.isEmpty())
            throw new ContestException(ContestErrorCode.NOT_FOUND);

        contestTeamRepository.delete(teamContest.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContestDetailDto> loadAllContest(SecurityAccount securityAccount, TeamIdxDto dto) {
        Account account = (securityAccount != null ? securityAccount.getAccount() : null);

        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (TeamContest teamContest : team.getTeamContests())
            dtos.add(ContestDetailDto.toDto(account, teamContest.getContest()));
        return dtos;
    }

    // Recruit
    @Override
    @Transactional(readOnly = true)
    public TeamRecruitDetailDto loadRecruit(SecurityAccount securityAccount, TeamRecruitDto dto) {
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        TeamRecruit teamRecruit = team.getTeamRecruit(contest)
                .orElseThrow(() -> new SystemException(SystemErrorCode.DATA_NOT_FOUND));
        return TeamRecruitDetailDto.toDto(teamRecruit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamRecruitDetailDto> loadAllRecruit(SecurityAccount securityAccount, TeamIdxDto dto) {
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        List<TeamRecruitDetailDto> dtos = new ArrayList<>();
        for (TeamRecruit teamRecruit : team.getTeamRecruits())
            dtos.add(TeamRecruitDetailDto.toDto(teamRecruit));
        return dtos;
    }

    @Override
    @Transactional
    public void updateRecruit(SecurityAccount securityAccount, TeamRecruitEditDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        if (!team.containsContest(contest))
            throw new ContestException(ContestErrorCode.NOT_FOUND);

        Optional<TeamRecruit> optionalTeamRecruit = teamRecruitRepository.findByTeamAndContest(team, contest);
        if (optionalTeamRecruit.isPresent()) {
            TeamRecruit teamRecruit = optionalTeamRecruit.get();
            teamRecruit.setPosition(dto.getPosition());
            teamRecruit.setRole(dto.getRole());
            teamRecruit.setQualification(dto.getQualification());
            teamRecruit.setSpecial(dto.getSpecial());
        } else {
            TeamRecruit teamRecruit = TeamRecruit.builder()
                    .team(team)
                    .contest(contest)

                    .position(dto.getPosition())
                    .role(dto.getRole())
                    .qualification(dto.getQualification())
                    .special(dto.getSpecial())
                    .build();
            teamRecruitRepository.save(teamRecruit);
        }
    }
}
