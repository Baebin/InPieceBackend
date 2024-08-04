package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.TeamRecruit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamRecruitDetailDto {
    @JsonProperty("team_idx")
    private Long teamIdx;
    @JsonProperty("contest_idx")
    private Long contestIdx;

    private String position;
    private String role;
    @JsonProperty("qual")
    private String qualification;
    private String special;

    public static TeamRecruitDetailDto toDto(TeamRecruit teamRecruit) {
        return TeamRecruitDetailDto.builder()
                .teamIdx(teamRecruit.getTeam().getIdx())
                .contestIdx(teamRecruit.getContest().getIdx())

                .position(teamRecruit.getPosition())
                .role(teamRecruit.getRole())
                .qualification(teamRecruit.getQualification())
                .special(teamRecruit.getSpecial())
                .build();
    }
}
