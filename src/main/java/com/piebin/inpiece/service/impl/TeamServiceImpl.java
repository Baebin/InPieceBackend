package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.AccountException;
import com.piebin.inpiece.exception.TeamException;
import com.piebin.inpiece.exception.entity.AccountErrorCode;
import com.piebin.inpiece.exception.entity.TeamErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamMember;
import com.piebin.inpiece.model.dto.team.TeamCreateDto;
import com.piebin.inpiece.model.dto.team.TeamMemberDto;
import com.piebin.inpiece.repository.AccountRepository;
import com.piebin.inpiece.repository.TeamMemberRepository;
import com.piebin.inpiece.repository.TeamRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final AccountRepository accountRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, TeamCreateDto dto) {
        Account account = securityAccount.getAccount();

        Team team = Team.builder()
                .name(dto.getName())
                .contest(dto.getContest())
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
        if (!team.isMember(eAccount))
            throw new TeamException(TeamErrorCode.IS_NON_MEMBER);
        teamMemberRepository.deleteByTeamAndAccount(team, eAccount);
    }
}
