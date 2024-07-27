package com.piebin.inpiece.model.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDescriptionDto {
    @NotBlank(message = "한줄 설명을 입력해주세요.")
    private String description;
}
