package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.*;
import com.piebin.inpiece.model.dto.team_recruit.TeamRecruitDetailDto;
import com.piebin.inpiece.model.dto.team.TeamDetailDto;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContestService {
    // Utility
    void create(SecurityAccount securityAccount, ContestCreateDto dto);

    // Getter
    ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto);
    ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, ContestIdxDto dto) throws IOException;

    List<ContestDetailDto> loadAll(SecurityAccount securityAccount, String filter, String sort, int page, int count);
    List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount);
    List<TeamDetailDto> loadAllTeam(SecurityAccount securityAccount, ContestIdxDto dto);
    List<TeamRecruitDetailDto> loadAllRecruit(SecurityAccount securityAccount, ContestIdxDto dto);

    // Setter
    void edit(SecurityAccount securityAccount, ContestEditDto dto) throws IOException;
    void editImage(SecurityAccount securityAccount, MultipartFile file, ContestIdxDto dto) throws IOException;
    void editRecommend(SecurityAccount securityAccount, ContestRecommendDto dto);
}
