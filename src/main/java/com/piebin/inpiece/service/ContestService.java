package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.security.SecurityAccount;

public interface ContestService {
    void create(SecurityAccount securityAccount, ContestCreateDto dto);
}
