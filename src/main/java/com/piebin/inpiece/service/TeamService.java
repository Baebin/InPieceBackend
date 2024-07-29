package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.team.TeamCreateDto;
import com.piebin.inpiece.model.dto.team.TeamMemberDto;
import com.piebin.inpiece.security.SecurityAccount;

public interface TeamService {
    void create(SecurityAccount securityAccount, TeamCreateDto dto);
    void invite(SecurityAccount securityAccount, TeamMemberDto dto);
    void remove(SecurityAccount securityAccount, TeamMemberDto dto);
}
