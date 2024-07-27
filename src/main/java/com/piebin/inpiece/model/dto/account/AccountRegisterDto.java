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
public class AccountRegisterDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String name;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
