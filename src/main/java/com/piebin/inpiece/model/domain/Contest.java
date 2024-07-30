package com.piebin.inpiece.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
    private List<Team> teams = new ArrayList<>();

    @Column(name = "rec_count")
    @OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
    private List<ContestRecCount> recCounts = new ArrayList<>();

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
