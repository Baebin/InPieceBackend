package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {
    @NotNull(message = "팀 식별 번호를 입력해주세요.")
    @JsonProperty("team_idx")
    private Long teamIdx;
    @NotNull(message = "게정 고유 식별 번호를 입력해주세요.")
    @JsonProperty("account_idx")
    private Long accountIdx;
}
