package com.piebin.inpiece.model.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStudentIdDto {
    @NotBlank(message = "학번을 입력해주세요.")
    @JsonProperty("student_id")
    private String studentId;
}
