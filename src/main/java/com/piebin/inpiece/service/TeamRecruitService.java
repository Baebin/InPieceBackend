package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.team.TeamIdxDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDto;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitEditDto;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamRecruitService {
    // Getter
    TeamRecruitDetailDto load(SecurityAccount securityAccount, TeamRecruitDto dto);
    List<TeamRecruitDetailDto> loadAll(SecurityAccount securityAccount, TeamIdxDto dto);

    // Setter
    void update(SecurityAccount securityAccount, TeamRecruitEditDto dto);

    // File
    ResponseEntity<byte[]> loadForm(SecurityAccount securityAccount, TeamRecruitDto dto) throws IOException;
    void uploadForm(SecurityAccount securityAccount, MultipartFile file, TeamRecruitDto dto) throws IOException;
}
