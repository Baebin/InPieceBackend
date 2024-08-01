package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.model.dto.contest.ContestRecommendDto;
import com.piebin.inpiece.security.SecurityAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContestService {
    // Utility
    void create(SecurityAccount securityAccount, ContestCreateDto dto);

    // Setter
    void editImage(SecurityAccount securityAccount, MultipartFile file, ContestIdxDto dto) throws IOException;
    void editRecommend(SecurityAccount securityAccount, ContestRecommendDto dto);

    // Getter
    ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, ContestIdxDto dto) throws IOException;

    ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto);
    List<ContestDetailDto> loadAll(SecurityAccount securityAccount, String filter, String sort, int page, int count);
    List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount);
}
