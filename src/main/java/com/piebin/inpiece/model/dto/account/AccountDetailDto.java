package com.piebin.inpiece.model.dto.account;

import com.piebin.inpiece.model.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailDto {
    private Long idx;
    private String name;

    public static AccountDetailDto toDto(Account account) {
        return AccountDetailDto.builder()
                .idx(account.getIdx())
                .name(account.getName())
                .build();
    }
}
