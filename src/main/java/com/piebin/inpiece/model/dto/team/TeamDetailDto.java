package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDetailDto {
    private Long idx;
    private String name;

    private String owner;
    @JsonProperty("owner_idx")
    private Long ownerIdx;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;

    public static TeamDetailDto toDto(Team team) {
        Account owner = team.getOwner();
        return TeamDetailDto.builder()
                .idx(team.getIdx())
                .name(team.getName())

                .owner(owner.getName())
                .ownerIdx(owner.getIdx())

                .regDate(team.getRegDate())
                .build();
    }
}
