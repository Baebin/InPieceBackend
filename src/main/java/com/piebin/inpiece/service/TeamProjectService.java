package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.team_project.TeamProjectCreateDto;
import com.piebin.inpiece.model.dto.team_project.TeamProjectIdxDto;
import com.piebin.inpiece.security.SecurityAccount;

public interface TeamProjectService {
    // Utility
    void create(SecurityAccount securityAccount, TeamProjectCreateDto dto);
    void delete(SecurityAccount securityAccount, TeamProjectIdxDto dto);
}
