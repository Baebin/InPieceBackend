package com.piebin.inpiece.model.dto.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDetailDto {
    private String name;
    private String ext;
    private byte[] bytes;

    public static ResponseEntity<byte[]> toResponseEntity(FileDetailDto dto) {
        MediaType mediaType = null;
        if (!ObjectUtils.isEmpty(dto.getExt())) {
            if (dto.getExt().contains("jpeg"))
                mediaType = MediaType.IMAGE_JPEG;
            else if (dto.getExt().contains("png"))
                mediaType = MediaType.IMAGE_PNG;
            else if (dto.getExt().contains("pdf"))
                mediaType = MediaType.APPLICATION_PDF;
            else mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + dto.getName() + "." + dto.getExt())
                .contentType(mediaType)
                .body(dto.getBytes());
    }
}
