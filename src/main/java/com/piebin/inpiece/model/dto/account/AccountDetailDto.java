package com.piebin.inpiece.model.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailDto {
    private Long idx;
    private String name;
    private String phone;
    private String description;

    private String major;
    @JsonProperty("student_id")
    private String studentId;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;

    public static AccountDetailDto toDto(Account account) {
        return AccountDetailDto.builder()
                .idx(account.getIdx())
                .name(account.getName())
                .phone(account.getPhone())
                .description(account.getDescription())

                .major(account.getMajor())
                .studentId(account.getStudentId())

                .regDate(account.getRegDate())
                .build();
    }
}
