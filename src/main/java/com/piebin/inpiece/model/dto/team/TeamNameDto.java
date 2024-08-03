package com.piebin.inpiece.model.dto.team;

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
public class TeamNameDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    @JsonProperty("idx")
    private Long idx;
    @NotBlank(message = "팀명을 입력해주세요.")
    private String name;
}
