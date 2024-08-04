package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.TeamException;
import com.piebin.inpiece.exception.entity.TeamErrorCode;
import com.piebin.inpiece.model.domain.*;
import com.piebin.inpiece.model.dto.team_project.*;
import com.piebin.inpiece.model.entity.SearchFilter;
import com.piebin.inpiece.model.entity.SearchSort;
import com.piebin.inpiece.repository.TeamProjectRecommendRepository;
import com.piebin.inpiece.repository.TeamProjectRepository;
import com.piebin.inpiece.repository.TeamRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.TeamProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamProjectServiceImpl implements TeamProjectService {
    private final TeamRepository teamRepository;
    private final TeamProjectRepository teamProjectRepository;
    private final TeamProjectRecommendRepository teamProjectRecommendRepository;

    // Utility
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

    // Getter
    @Override
    @Transactional
    public TeamProjectDetailDto load(SecurityAccount securityAccount, TeamProjectIdxDto dto) {
        TeamProject teamProject = teamProjectRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.PROJECT_NOT_FOUND));
        teamProject.setViewCount(teamProject.getViewCount() + 1);
        return TeamProjectDetailDto.toDto(teamProject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamProjectDetailDto> loadAll(SecurityAccount securityAccount, String filter, String sort, int page, int count) {
        List<TeamProject> projects;
        PageRequest pageRequest = PageRequest.of(page, count);

        // Default : REG_DATE, DESC
        if (filter.equalsIgnoreCase(SearchFilter.REC_COUNT.name())) {
            // REC_COUNT
            if (sort.equalsIgnoreCase(SearchSort.ASC.name()))
                projects = teamProjectRepository.findAllByOrderByRecommendsAsc(pageRequest);
            else projects = teamProjectRepository.findAllByOrderByRecommendsDesc(pageRequest);
        } else if (filter.equalsIgnoreCase(SearchFilter.VIEW_COUNT.name())) {
            // VIEW_COUNT
            if (sort.equalsIgnoreCase(SearchSort.ASC.name()))
                projects = teamProjectRepository.findAllByOrderByViewCountAscRegDateAsc(pageRequest);
            else projects = teamProjectRepository.findAllByOrderByViewCountDescRegDateDesc(pageRequest);
        } else {
            // REG_DATE
            if (sort.equalsIgnoreCase(SearchSort.ASC.name()))
                projects = teamProjectRepository.findAllByOrderByRegDateAsc(pageRequest);
            else projects = teamProjectRepository.findAllByOrderByRegDateDesc(pageRequest);
        }
        List<TeamProjectDetailDto> dtos = new ArrayList<>();
        for (TeamProject project : projects)
            dtos.add(TeamProjectDetailDto.toDto(project));
        return dtos;
    }


    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, TeamProjectEditDto dto) {
        Account account = securityAccount.getAccount();
        TeamProject teamProject = teamProjectRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.PROJECT_NOT_FOUND));
        Team team = teamProject.getTeam();
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        teamProject.setName(dto.getName());
        teamProject.setDescription(dto.getDescription());

        teamProject.setPosition(dto.getPosition());
        teamProject.setRole(dto.getRole());
        teamProject.setQualification(dto.getQualification());
        teamProject.setSpecial(dto.getSpecial());

        teamProject.setCategories(dto.getCategories());

        teamProject.setEndDate(dto.getEndDate());
    }

    @Override
    @Transactional
    public void editRecommend(SecurityAccount securityAccount, TeamProjectRecommendDto dto) {
        Account account = securityAccount.getAccount();
        TeamProject teamProject = teamProjectRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.PROJECT_NOT_FOUND));
        Optional<TeamProjectRecommend> optionalRecommend = teamProject.getRecommend(account);
        if (dto.getState()) {
            if (optionalRecommend.isPresent())
                return;
            TeamProjectRecommend recommend = TeamProjectRecommend.builder()
                    .teamProject(teamProject)
                    .account(account)
                    .build();
            teamProjectRecommendRepository.save(recommend);
        } else {
            if (optionalRecommend.isEmpty())
                return;
            teamProjectRecommendRepository.delete(optionalRecommend.get());
        }
    }
}
