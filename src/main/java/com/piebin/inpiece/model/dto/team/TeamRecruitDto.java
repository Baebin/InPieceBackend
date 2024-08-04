package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRecruitDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    @JsonProperty("team_idx")
    private Long team_idx;
    @NotNull(message = "대회 고유 식별 번호를 입력해주세요.")
    @JsonProperty("contest_idx")
    private Long contest_idx;

    public Long getTeamIdx() {
        return team_idx;
    }
    public Long getContestIdx() {
        return contest_idx;
    }
}
