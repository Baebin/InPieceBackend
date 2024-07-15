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
    @NotBlank
    private String id;
    @NotBlank
    private String password;
}
