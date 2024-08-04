package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.team_project.*;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamProjectService {
    // Utility
    void create(SecurityAccount securityAccount, TeamProjectCreateDto dto);
    void delete(SecurityAccount securityAccount, TeamProjectIdxDto dto);

    // Getter
    TeamProjectDetailDto load(SecurityAccount securityAccount, TeamProjectIdxDto dto);
    List<TeamProjectDetailDto> loadAll(SecurityAccount securityAccount, String filter, String sort, int page, int count);

    // Setter
    void edit(SecurityAccount securityAccount, TeamProjectEditDto dto);
    void editRecommend(SecurityAccount securityAccount, TeamProjectRecommendDto dto);

    // File
    ResponseEntity<byte[]> loadForm(SecurityAccount securityAccount, TeamProjectIdxDto dto) throws IOException;
    void uploadForm(SecurityAccount securityAccount, MultipartFile file, TeamProjectIdxDto dto) throws IOException;
}
