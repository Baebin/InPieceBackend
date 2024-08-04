package com.piebin.inpiece.model.dto.team_project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Team;
import com.piebin.inpiece.model.domain.TeamProject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamProjectDetailDto {
    @JsonProperty("team_idx")
    private Long teamIdx;
    @JsonProperty("project_idx")
    private Long projectIdx;

    private String owner;
    @JsonProperty("owner_idx")
    private Long ownerIdx;

    @JsonProperty("team_name")
    private String teamName;
    @JsonProperty("project_name")
    private String projectName;
    private String description;

    private String position;
    private String role;
    @JsonProperty("qual")
    private String qualification;
    private String special;

    private Boolean recommend;
    @JsonProperty("rec_count")
    private Long recCount;
    @JsonProperty("view_count")
    private Long viewCount;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    public static TeamProjectDetailDto toDto(Account account, TeamProject teamProject) {
        Team team = teamProject.getTeam();
        Account owner = team.getOwner();
        return TeamProjectDetailDto.builder()
                .teamIdx(team.getIdx())
                .projectIdx(teamProject.getIdx())

                .owner(owner.getName())
                .ownerIdx(owner.getIdx())

                .teamName(team.getName())
                .projectName(teamProject.getName())
                .description(teamProject.getDescription())

                .position(teamProject.getPosition())
                .role(teamProject.getRole())
                .qualification(teamProject.getQualification())
                .special(teamProject.getSpecial())

                .recommend(account != null ? teamProject.getRecommend(account).isPresent() : false)
                .recCount((long) teamProject.getRecommends().size())
                .viewCount(teamProject.getViewCount())

                .regDate(teamProject.getRegDate())
                .endDate(teamProject.getEndDate())
                .build();
    }
}
