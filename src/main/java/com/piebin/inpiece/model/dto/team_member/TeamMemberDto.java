package com.piebin.inpiece.model.dto.team_member;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    @JsonProperty("team_idx")
    private Long teamIdx;
    @NotNull(message = "게정 고유 식별 번호를 입력해주세요.")
    @JsonProperty("account_idx")
    private Long accountIdx;
}
