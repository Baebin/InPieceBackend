package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.ContestException;
import com.piebin.inpiece.exception.PermissionException;
import com.piebin.inpiece.exception.entity.ContestErrorCode;
import com.piebin.inpiece.exception.entity.PermissionErrorCode;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
import com.piebin.inpiece.model.domain.ContestRecCount;
import com.piebin.inpiece.model.dto.contest.ContestCreateDto;
import com.piebin.inpiece.model.dto.contest.ContestDetailDto;
import com.piebin.inpiece.model.dto.contest.ContestIdxDto;
import com.piebin.inpiece.model.dto.image.ImageDetailDto;
import com.piebin.inpiece.model.dto.image.ImageDto;
import com.piebin.inpiece.repository.ContestRecCountRepository;
import com.piebin.inpiece.repository.ContestRepository;
import com.piebin.inpiece.security.SecurityAccount;
import com.piebin.inpiece.service.ContestService;
import com.piebin.inpiece.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final ContestRecCountRepository contestRecCountRepository;

    private final ImageService imageService;

    // Utility
    @Override
    @Transactional
    public void create(SecurityAccount securityAccount, ContestCreateDto dto) {
        Account account = securityAccount.getAccount();
        Contest contest = Contest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tags(dto.getTags())
                .owner(account)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        contestRepository.save(contest);
    }

    // Setter
    @Override
    @Transactional
    public void editImage(SecurityAccount securityAccount, MultipartFile file, ContestIdxDto dto) throws IOException {
        Account account = securityAccount.getAccount();
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        if (!contest.getOwner().getIdx().equals(account.getIdx()))
            throw new PermissionException(PermissionErrorCode.UNAUTHORIZED);
        String path = "contest/" + contest.getIdx();
        String name = "image";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        imageService.upload(file, imageDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> loadImage(SecurityAccount securityAccount, ContestIdxDto dto) throws IOException {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        String path = "contest/" + contest.getIdx();
        String name = "image";
        ImageDto imageDto = ImageDto.builder().path(path).name(name).build();
        ImageDetailDto imageDetailDto = imageService.download(imageDto);;
        return ImageDetailDto.toResponseEntity(imageDetailDto);
    }

    // Getter
    @Override
    @Transactional(readOnly = true)
    public ContestDetailDto load(SecurityAccount securityAccount, ContestIdxDto dto) {
        Contest contest = contestRepository.findByIdx(dto.getIdx())
                .orElseThrow(() -> new ContestException(ContestErrorCode.NOT_FOUND));
        return ContestDetailDto.toDto(contest);
    }

    @Override
    @Transactional
    public List<ContestDetailDto> loadAllMyContest(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (Contest contest : contestRepository.findAllByOwnerOrderByIdxDesc(account))
            dtos.add(ContestDetailDto.toDto(contest));
        return dtos;
    }

    @Override
    @Transactional
    public List<ContestDetailDto> loadAllWithMyRecCount(SecurityAccount securityAccount) {
        Account account = securityAccount.getAccount();
        List<ContestDetailDto> dtos = new ArrayList<>();
        for (ContestRecCount contestRecCount : contestRecCountRepository.findAllByAccountOrderByIdxDesc(account))
            dtos.add(ContestDetailDto.toDto(contestRecCount.getContest()));
        return dtos;
    }
}
