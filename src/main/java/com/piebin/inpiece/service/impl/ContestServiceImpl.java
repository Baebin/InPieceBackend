package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.repository.ContestRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, ContestCreateDto dto) {
        Account account = securityAccount.getAccount();
        Contest contest = Contest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .owner(account)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        contestRepository.save(contest);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public ContestDetailDto loadDetail(SecurityAccount securityAccount, ContestIdxDto dto) {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        return ContestDetailDto.toDto(contest);
    }
}
