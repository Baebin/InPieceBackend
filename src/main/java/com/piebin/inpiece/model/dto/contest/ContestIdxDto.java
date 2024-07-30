package com.piebin.inpiece.model.dto.contest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestIdxDto {
    @NotNull(message = "대회 고유 식별 변호를 입력해주세요.")
    private Long idx;
}
