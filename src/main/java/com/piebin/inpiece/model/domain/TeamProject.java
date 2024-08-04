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

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;
}
