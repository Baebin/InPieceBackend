package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.security.SecurityAccount;

import java.util.List;

public interface ContestService {
    // Utility
    void create(SecurityAccount securityAccount, ContestCreateDto dto);

    // Getter
    ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto);
    List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount);
    List<ContestDetailDto> loadAllWithMyRecCount(SecurityAccount securityAccount);
}
