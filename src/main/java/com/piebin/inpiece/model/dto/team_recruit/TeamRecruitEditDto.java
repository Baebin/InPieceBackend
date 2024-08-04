package com.piebin.inpiece.model.dto.team_recruit;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRecruitEditDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    @JsonProperty("team_idx")
    private Long teamIdx;
    @NotNull(message = "대회 고유 식별 번호를 입력해주세요.")
    @JsonProperty("contest_idx")
    private Long contestIdx;

    @NotBlank(message = "포지션 정보를 입력해주세요.")
    private String position;
    @NotBlank(message = "주요업무 정보를 입력해주세요.")
    private String role;
    @NotBlank(message = "자격요건 정보를 입력해주세요.")
    @JsonProperty("qual")
    private String qualification;
    @NotBlank(message = "우대사항 정보를 입력해주세요.")
    private String special;
}
