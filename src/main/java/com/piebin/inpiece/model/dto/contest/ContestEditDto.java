package com.piebin.inpiece.model.dto.contest;

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
public class ContestEditDto {
    @NotNull(message = "대회 고유 식별 번호를 입력해주세요.")
    @JsonProperty("idx")
    private Long idx;

    @NotBlank(message = "대회명을 입력해주세요.")
    private String name;
    private String description;

    private List<String> tags;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("end_date")
    private LocalDateTime endDate;
}
