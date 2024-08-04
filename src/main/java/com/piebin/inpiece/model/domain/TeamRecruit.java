package com.piebin.inpiece.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TeamRecruit {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Contest contest;

    private String position;

    private String role;

    @Column(name = "qual")
    private String qualification;

    private String special;

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
