package com.piebin.inpiece.service.impl;

import com.piebin.inpiece.exception.FileException;
import com.piebin.inpiece.exception.entity.FileErrorCode;
import com.piebin.inpiece.model.domain.FileEntity;
import com.piebin.inpiece.model.dto.file.FileDetailDto;
import com.piebin.inpiece.model.dto.file.FileDto;
import com.piebin.inpiece.repository.FileEntityRepository;
import com.piebin.inpiece.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    public static final String DIRECTORY = "files";

    private final FileEntityRepository fileEntityRepository;

    private String getPath() {
        return new File("").getAbsolutePath() + "/";
    }

    private String getFilePath(String path, String name, String ext) {
        return (getPath()
                + DIRECTORY + "/"
                + path + "/"
                + name + "." + ext)
                .replace("\\", "/");
    }

    private String getType(MultipartFile file) {
        return getType(file.getOriginalFilename(), file.getContentType());
    }

    private String getType(String name, String type) {
        log.info("type: {}", type);
        if (type.contains("image/png"))
            return "png";
        if (type.contains("image/jpeg"))
            return "jpeg";
        if (type.contains("application/pdf"))
            return "pdf";
        if (type.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            return "docx";
        if (type.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation"))
            return "pptx";
        if (type.contains("application/octet-stream")) {
            if (name.endsWith(".hwp"))
                return "hwp";
            if (name.endsWith(".hwpx"))
                return "hwpx";
        }
        return null;
    }

    // Getter
    @Override
    public File getFile(String path, String name, String ext) {
        return new File(getFilePath(path, name, ext));
    }

    @Override
    @Transactional(readOnly = true)
    public FileDetailDto download(FileDto dto) throws IOException {
        FileEntity fileEntity = fileEntityRepository.findByPathAndName(dto.getPath(), dto.getName())
                .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
        File file = getFile(fileEntity.getPath(), fileEntity.getName(), fileEntity.getExt());
        if (!file.exists())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return FileDetailDto.builder()
                .name(dto.getName())
                .ext(fileEntity.getExt())
                .bytes(bytes)
                .build();
    }

    // Setter
    @Override
    @Transactional
    public void upload(MultipartFile file, FileDto dto) throws IOException {
        if (file.isEmpty())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        String ext = getType(file);
        if (ObjectUtils.isEmpty(ext))
            throw new FileException(FileErrorCode.EXT_INCORRECT);
        File img = getFile(dto.getPath(), dto.getName(), ext);
        if (!img.exists())
            img.mkdirs();
        if (img.exists())
            img.delete();

        // Upload
        file.transferTo(img);

        // Database Update
        Optional<FileEntity> optional = fileEntityRepository.findByPathAndName(dto.getPath(), dto.getName());
        if (optional.isPresent()) {
            FileEntity fileEntity = optional.get();
            fileEntity.setExt(ext);
            fileEntity.setEditDate(LocalDateTime.now());
        }
        else fileEntityRepository.save(
                FileEntity.builder()
                        .path(dto.getPath())
                        .name(dto.getName())
                        .ext(ext)
                        .build());
    }

    // Deleter
    @Override
    @Transactional
    public void delete(FileDto dto) {
        FileEntity fileEntity = fileEntityRepository.findByPathAndName(dto.getPath(), dto.getName())
                .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
        File file = getFile(fileEntity.getPath(), fileEntity.getName(), fileEntity.getExt());
        if (!file.exists())
            throw new FileException(FileErrorCode.FILE_NOT_FOUND);
        if (file.delete())
            fileEntityRepository.delete(fileEntity);
    }
}
