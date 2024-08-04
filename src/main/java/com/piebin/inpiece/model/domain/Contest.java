package com.piebin.inpiece.model.domain;

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
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    private String description;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
    private List<TeamContest> teamContests = new ArrayList<>();

    @OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
    private List<TeamRecruit> teamRecruits = new ArrayList<>();

    @OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
    private List<ContestRecommend> recommends = new ArrayList<>();

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public Optional<ContestRecommend> getRecommend(Account account) {
        for (ContestRecommend recommend : recommends)
            if (recommend.getAccount().getIdx().equals(account.getIdx()))
                return Optional.of(recommend);
        return Optional.empty();
    }
}
