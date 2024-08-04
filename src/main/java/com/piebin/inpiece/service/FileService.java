package com.piebin.inpiece.service;

import com.piebin.inpiece.model.dto.file.FileDetailDto;
import com.piebin.inpiece.model.dto.file.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    // Getter
    File getFile(String path, String name, String ext);
    FileDetailDto download(FileDto dto) throws IOException;

    // Setter
    void upload(MultipartFile file, FileDto dto) throws IOException;

    // Deleter
    void delete(FileDto dto);
}
