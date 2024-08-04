package com.piebin.inpiece.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
}
