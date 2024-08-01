package com.piebin.inpiece.model.dto.contest;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContestRecommendDto {
    @NotNull(message = "대회 고유 식별 변호를 입력해주세요.")
    private Long idx;
    @NotNull(message = "대회 추천 상태를 입력해주세요.")
    private Boolean state;
}
