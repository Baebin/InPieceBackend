package com.piebin.inpiece.model.dto.contest;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestCreateDto {
    @NotBlank(message = "대회명을 입력해주세요.")
    private String name;
    private String description;
}
