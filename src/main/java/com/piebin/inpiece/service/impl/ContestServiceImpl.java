package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.PermissionException;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.exception.entity.PermissionErrorCode;
import com.piebin.inpiece.model.domain.*;
import com.piebin.inpiece.model.dto.contest.*;
import com.piebin.inpiece.model.dto.image.ImageDetailDto;
import com.piebin.inpiece.model.dto.image.ImageDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team.TeamDetailDto;
import com.piebin.inpiece.model.entity.ContestFilter;
import com.piebin.inpiece.model.entity.ContestSort;
import com.piebin.inpiece.repository.ContestRecCountRepository;
import com.piebin.inpiece.repository.ContestRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import com.piebin.inpiece.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final ContestRecCountRepository contestRecCountRepository;

    private final ImageService imageService;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, ContestCreateDto dto) {
        Account account = securityAccount.getAccount();
        Contest contest = Contest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tags(dto.getTags())
                .owner(account)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        contestRepository.save(contest);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, ContestIdxDto dto) throws IOException {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        String path = "contest/" + contest.getIdx();
        String name = "image";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);;
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto) {
        Account account = (securityAccount != null ? securityAccount.getAccount() : null);
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        return ContestDetailDto.toDto(account, contest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContestDetailDto> loadAll(SecurityAccount securityAccount, String filter, String sort, int page, int count) {
        Account account = (securityAccount != null ? securityAccount.getAccount() : null);

        List<Contest> contests;
        PageRequest pageRequest = PageRequest.of(page, count);

        // Default : REG_DATE, DESC
        if (filter.equalsIgnoreCase(ContestFilter.REC_COUNT.name())) {
            if (sort.equalsIgnoreCase(ContestSort.ASC.name()))
                contests = contestRepository.findAllByOrderByRecommendsAsc(pageRequest);
            else contests = contestRepository.findAllByOrderByRecommendsDesc(pageRequest);
        } else {
            if (sort.equalsIgnoreCase(ContestSort.ASC.name()))
                contests = contestRepository.findAllByOrderByRegDateAsc(pageRequest);
            else contests = contestRepository.findAllByOrderByRegDateDesc(pageRequest);
        }
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (Contest contest : contests)
            dtos.add(ContestDetailDto.toDto(account, contest));
        return dtos;
    }

    @Override
    @Transactional
    public List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (Contest contest : contestRepository.findAllByOwnerOrderByIdxDesc(account))
            dtos.add(ContestDetailDto.toDto(account, contest));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamDetailDto> loadAllTeam(SecurityAccount securityAccount, ContestIdxDto dto) {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        List<TeamDetailDto> dtos = new ArrayList<>();
        for (TeamContest teamContest : contest.getTeamContests())
            dtos.add(TeamDetailDto.toDto(teamContest.getTeam()));
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamRecruitDetailDto> loadAllRecruit(SecurityAccount securityAccount, ContestIdxDto dto) {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        List<TeamRecruitDetailDto> dtos = new ArrayList<>();
        for (TeamRecruit teamRecruit : contest.getTeamRecruits())
            dtos.add(TeamRecruitDetailDto.toDto(teamRecruit));
        return dtos;
    }

    // Setter
    @Override
    @Transactional
    public void edit(SecurityAccount securityAccount, ContestEditDto dto) {
        Account account = securityAccount.getAccount();
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        if (!contest.getOwner().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);

        contest.setName(dto.getName());
        contest.setDescription(dto.getDescription());
        contest.setTags(dto.getTags());
        contest.setStartDate(dto.getStartDate());
        contest.setEndDate(dto.getEndDate());
    }

    @Override
    @Transactional
    public void editImage(SecurityAccount securityAccount, MultipartFile file, ContestIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        if (!contest.getOwner().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);
        String path = "contest/" + contest.getIdx();
        String name = "image";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional
    public void editRecommend(SecurityAccount securityAccount, ContestRecommendDto dto) {
        Account account = securityAccount.getAccount();
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        Optional<ContestRecommend> optionalRecommend = contest.getRecommend(account);
        if (dto.getState()) {
            if (optionalRecommend.isPresent())
                return;
            ContestRecommend recommend = ContestRecommend.builder()
                    .contest(contest)
                    .account(account)
                    .build();
            contestRecCountRepository.save(recommend);
        } else {
            if (optionalRecommend.isEmpty())
                return;
            contestRecCountRepository.delete(optionalRecommend.get());
        }
    }
}
