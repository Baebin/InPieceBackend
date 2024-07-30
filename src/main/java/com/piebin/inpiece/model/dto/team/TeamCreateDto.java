package com.piebin.inpiece.model.dto.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateDto {
    @NotBlank(message = "팀명을 입력해주세요.")
    private String name;
    @NotNull(message = "대회 고유 식별 번호를 입력해주세요.")
    @JsonProperty("contest_idx")
    private Long contestIdx;
}
