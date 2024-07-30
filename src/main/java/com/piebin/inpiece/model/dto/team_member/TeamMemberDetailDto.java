package com.piebin.inpiece.model.dto.team_member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamMember;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDetailDto {
    @JsonProperty("team_idx")
    private Long teamIdx;
    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("contest_name")
    private String contestName;

    private String role;

    public static TeamMemberDetailDto toDto(TeamMember teamMember) {
        Team team = teamMember.getTeam();
        return TeamMemberDetailDto.builder()
                .teamIdx(team.getIdx())
                .teamName(team.getName())
                .contestName(team.getContest().getName())

                .role(teamMember.getRole())
                .build();
    }
}
