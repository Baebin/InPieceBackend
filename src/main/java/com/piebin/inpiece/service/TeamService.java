package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.team.*;
import com.piebin.inpiece.model.dto.team.TeamDetailDto;
import com.piebin.inpiece.model.dto.team_member.TeamMemberDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitEditDto;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamService {
    // Utility
    void create(SecurityAccount securityAccount, TeamCreateDto dto);
    void delete(SecurityAccount securityAccount, TeamIdxDto dto);
    void invite(SecurityAccount securityAccount, TeamMemberDto dto);
    void remove(SecurityAccount securityAccount, TeamMemberDto dto);

    // Getter
    TeamDetailDto load(SecurityAccount securityAccount, TeamIdxDto dto);
    List<TeamDetailDto> loadAllMyTeam(SecurityAccount securityAccount);
    List<TeamDetailDto> loadAllMyOwnTeam(SecurityAccount securityAccount);

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

    // Recruit Form
    ResponseEntity<byte[]> loadRecruitForm(SecurityAccount securityAccount, TeamRecruitDto dto) throws IOException;
    void uploadRecruitForm(SecurityAccount securityAccount, MultipartFile file, TeamRecruitDto dto) throws IOException;
}
