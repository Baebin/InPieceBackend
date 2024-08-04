package com.piebin.inpiece.model.dto.team_project;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamProjectRecommendDto {
    @NotNull(message = "팀 프로젝트 고유 식별 변호를 입력해주세요.")
    private Long idx;
    @NotNull(message = "팀 프로젝트 추천 상태를 입력해주세요.")
    private Boolean state;
}
