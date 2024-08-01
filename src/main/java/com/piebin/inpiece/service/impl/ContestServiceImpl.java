package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
import com.piebin.inpiece.model.domain.ContestRecCount;
import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.repository.ContestRecCountRepository;
import com.piebin.inpiece.repository.ContestRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final ContestRecCountRepository contestRecCountRepository;

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
    public ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto) {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        return ContestDetailDto.toDto(contest);
    }

    @Override
    @Transactional
    public List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (Contest contest : contestRepository.findAllByOwnerOrderByIdxDesc(account))
            dtos.add(ContestDetailDto.toDto(contest));
        return dtos;
    }

    @Override
    @Transactional
    public List<ContestDetailDto> loadAllWithMyRecCount(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (ContestRecCount contestRecCount : contestRecCountRepository.findAllByAccountOrderByIdxDesc(account))
            dtos.add(ContestDetailDto.toDto(contestRecCount.getContest()));
        return dtos;
    }
}
