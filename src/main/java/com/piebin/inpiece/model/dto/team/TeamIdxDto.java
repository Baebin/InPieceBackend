package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamIdxDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    @JsonProperty("idx")
    private Long idx;
}
