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
public class AccountEditDto {
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String name;
    @NotBlank(message = "학과를 입력해주세요.")
    private String major;
    @NotBlank(message = "학번을 입력해주세요.")
    @JsonProperty("student_id")
    private String studentId;
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phone;
    @NotBlank(message = "한줄 설명을 입력해주세요.")
    private String description;
}
