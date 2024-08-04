package com.piebin.inpiece.model.dto.contest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.piebin.inpiece.model.domain.Account;
import com.piebin.inpiece.model.domain.Contest;
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
public class ContestDetailDto {
    private Long idx;
    private String name;
    private String description;
    private List<String> tags;

    @JsonProperty("owner_name")
    private String ownerName;

    private Boolean recommend;
    @JsonProperty("rec_count")
    private Long recCount;
    @JsonProperty("view_count")
    private Long viewCount;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("reg_date")
    private LocalDateTime regDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    public static ContestDetailDto toDto(Account account, Contest contest) {
        return ContestDetailDto.builder()
                .idx(contest.getIdx())
                .name(contest.getName())
                .description(contest.getDescription())
                .tags(contest.getTags())

                .ownerName(contest.getOwner().getName())

                .recommend(account != null ? contest.getRecommend(account).isPresent() : false)
                .recCount((long) contest.getRecommends().size())
                .viewCount(contest.getViewCount())

                .regDate(contest.getRegDate())
                .startDate(contest.getStartDate())
                .endDate(contest.getEndDate())

                .build();
    }
}
