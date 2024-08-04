package com.piebin.inpiece.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamProject {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private Team team;

    private String name;
    private String description;

    private String position;
    private String role;
    @Column(name = "qual")
    private String qualification;
    private String special;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categories = new ArrayList<>();

    @OneToMany(mappedBy = "teamProject", cascade = CascadeType.REMOVE)
    private List<TeamProjectRecommend> recommends = new ArrayList<>();

    @JsonProperty("view_count")
    @Builder.Default
    private Long viewCount = 0L;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Optional<TeamProjectRecommend> getRecommend(Account account) {
        for (TeamProjectRecommend recommend : recommends)
            if (recommend.getAccount().getIdx().equals(account.getIdx()))
                return Optional.of(recommend);
        return Optional.empty();
    }
}
