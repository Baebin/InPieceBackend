package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.TeamException;
import com.piebin.inpiece.exception.entity.TeamErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamProject;
import com.piebin.inpiece.model.dto.team_project.TeamProjectCreateDto;
import com.piebin.inpiece.model.dto.team_project.TeamProjectIdxDto;
import com.piebin.inpiece.repository.TeamProjectRepository;
import com.piebin.inpiece.repository.TeamRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamProjectServiceImpl implements TeamProjectService {
    private final TeamRepository teamRepository;
    private final TeamProjectRepository teamProjectRepository;

    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, TeamProjectCreateDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        TeamProject teamProject = TeamProject.builder()
                .team(team)

                .name(dto.getName())
                .description(dto.getDescription())

                .position(dto.getPosition())
                .role(dto.getRole())
                .qualification(dto.getQualification())
                .special(dto.getSpecial())

                .categories(dto.getCategories())

                .endDate(dto.getEndDate())
                .build();
        teamProjectRepository.save(teamProject);
    }

    @Override
    @Transactional
    public void delete(SecurityAccount securityAccount, TeamProjectIdxDto dto) {
        Account account = securityAccount.getAccount();
        TeamProject teamProject = teamProjectRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.PROJECT_NOT_FOUND));
        Team team = teamProject.getTeam();
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);
        teamProjectRepository.delete(teamProject);
    }
}
