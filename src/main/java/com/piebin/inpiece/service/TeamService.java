package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.team.*;
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
    List<TeamMemberDetailDto> loadAllMyTeam(SecurityAccount securityAccount);

    // Setter
    void editName(SecurityAccount securityAccount, TeamNameDto dto);

    // Contest
    void addContest(SecurityAccount securityAccount, TeamContestDto dto);
    void removeContest(SecurityAccount securityAccount, TeamContestDto dto);

    List<ContestDetailDto> loadAllContest(SecurityAccount securityAccount, TeamIdxDto dto);

    // Recruit
    TeamRecruitDetailDto loadRecruit(SecurityAccount securityAccount, TeamRecruitDto dto);
    List<TeamRecruitDetailDto> loadAllRecruit(SecurityAccount securityAccount, TeamIdxDto dto);
    void updateRecruit(SecurityAccount securityAccount, TeamRecruitEditDto dto);
}
