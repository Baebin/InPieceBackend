package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.team.TeamCreateDto;
import com.piebin.inpiece.model.dto.team.TeamIdxDto;
import com.piebin.inpiece.model.dto.team.TeamMemberDto;
import com.piebin.inpiece.model.dto.team_member.TeamMemberDetailDto;
import com.piebin.inpiece.security.SecurityAccount;

import java.util.List;

public interface TeamService {
    // Utility
    void create(SecurityAccount securityAccount, TeamCreateDto dto);
    void delete(SecurityAccount securityAccount, TeamIdxDto dto);
    void invite(SecurityAccount securityAccount, TeamMemberDto dto);
    void remove(SecurityAccount securityAccount, TeamMemberDto dto);

    // Getter
    List<TeamMemberDetailDto> loadAll(SecurityAccount securityAccount);
}
