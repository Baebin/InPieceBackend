package com.piebin.inpiece.model.dto.team_project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamProjectCreateDto {
    @NotNull(message = "팀 고유 식별 번호를 입력해주세요.")
    private Long idx;

    @NotBlank(message = "프로젝트명을 입력해주세요.")
    private String name;
    private String description;

    @NotBlank(message = "포지션 정보를 입력해주세요.")
    private String position;
    @NotBlank(message = "주요업무 정보를 입력해주세요.")
    private String role;
    @NotBlank(message = "자격요건 정보를 입력해주세요.")
    @JsonProperty("qual")
    private String qualification;
    @NotBlank(message = "우대사항 정보를 입력해주세요.")
    private String special;

    private List<String> categories;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("end_date")
    private LocalDateTime endDate;
}
