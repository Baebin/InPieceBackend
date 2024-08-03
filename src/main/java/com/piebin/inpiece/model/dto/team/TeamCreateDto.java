package com.piebin.inpiece.model.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateDto {
    @NotBlank(message = "팀명을 입력해주세요.")
    private String name;
}
