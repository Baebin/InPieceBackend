package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
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

    // Getter
    ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, ContestIdxDto dto) throws IOException;

    ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto);
    List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount);
    List<ContestDetailDto> loadAllWithMyRecCount(SecurityAccount securityAccount);
}
