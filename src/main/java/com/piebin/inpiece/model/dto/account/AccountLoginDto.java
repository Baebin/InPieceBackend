package com.piebin.inpiece.model.dto.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginDto {
    @NotBlank(message = "아이디을 입력해주세요.")
    private String id;
    @NotBlank(message = "비밀번호을 입력해주세요.")
    private String password;
}
