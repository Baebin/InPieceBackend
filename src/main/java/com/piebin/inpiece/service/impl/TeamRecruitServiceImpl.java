package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.SystemException;
import com.piebin.inpiece.exception.TeamException;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.exception.entity.SystemErrorCode;
import com.piebin.inpiece.exception.entity.TeamErrorCode;
import com.piebin.inpiece.model.domain.*;
import com.piebin.inpiece.model.dto.file.FileDetailDto;
import com.piebin.inpiece.model.dto.file.FileDto;
import com.piebin.inpiece.model.dto.team.TeamIdxDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitEditDto;
import com.piebin.inpiece.repository.ContestRepository;
import com.piebin.inpiece.repository.TeamRecruitRepository;
import com.piebin.inpiece.repository.TeamRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.FileService;
import com.piebin.inpiece.service.TeamRecruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamRecruitServiceImpl implements TeamRecruitService {
    private final ContestRepository contestRepository;
    private final TeamRepository teamRepository;
    private final TeamRecruitRepository teamRecruitRepository;

    private final FileService fileService;

    // Getter
    @Override
    @Transactional(readOnly = true)
    public TeamRecruitDetailDto load(SecurityAccount securityAccount, TeamRecruitDto dto) {
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
    public List<TeamRecruitDetailDto> loadAll(SecurityAccount securityAccount, TeamIdxDto dto) {
        Team team = teamRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        List<TeamRecruitDetailDto> dtos = new ArrayList<>();
        for (TeamRecruit teamRecruit : team.getTeamRecruits())
            dtos.add(TeamRecruitDetailDto.toDto(teamRecruit));
        return dtos;
    }

    // Setter
    @Override
    @Transactional
    public void update(SecurityAccount securityAccount, TeamRecruitEditDto dto) {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));

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

    // File
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadForm(SecurityAccount securityAccount, TeamRecruitDto dto) throws IOException {
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        TeamRecruit teamRecruit = team.getTeamRecruit(contest)
                .orElseThrow(() -> new SystemException(SystemErrorCode.DATA_NOT_FOUND));
        String path = "team/" + team.getIdx() + "/recruit/" + teamRecruit.getIdx();
        String name = "form";
        FileDto fileDto = FileDto.builder().path(path).name(name).build();
        FileDetailDto fileDetailDto = fileService.download(fileDto);
        return FileDetailDto.toResponseEntity(fileDetailDto);
    }

    @Override
    @Transactional
    public void uploadForm(SecurityAccount securityAccount, MultipartFile file, TeamRecruitDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Team team = teamRepository.findByIdx(dto.getTeamIdx())
                .orElseThrow(() -> new TeamException(TeamErrorCode.NOT_FOUND));
        if (!team.isOwner(account))
            throw new TeamException(TeamErrorCode.IS_NON_OWNER);

        Contest contest = contestRepository.findByIdx(dto.getContestIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        TeamRecruit teamRecruit = team.getTeamRecruit(contest)
                .orElseThrow(() -> new SystemException(SystemErrorCode.DATA_NOT_FOUND));

        String path = "team/" + team.getIdx() + "/recruit/" + teamRecruit.getIdx();
        String name = "form";
        FileDto fileDto = FileDto.builder().path(path).name(name).build();
        fileService.upload(file, fileDto);
    }
}
