package com.piebin.inpiece.model.dto.team_project;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamProjectIdxDto {
    @NotNull(message = "팀 프로젝트 고유 식별 번호를 입력해주세요.")
    private Long idx;
}
