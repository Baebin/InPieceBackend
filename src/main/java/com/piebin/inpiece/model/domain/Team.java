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
public class Team {
    @Id
    @Column(name = "idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    private String contest;

    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "team", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<TeamMember> members = new ArrayList<>();

    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    public boolean isOwner(Account account) {
        return (owner.getIdx().equals(account.getIdx()));
    }

    public boolean isMember(Account account) {
        for (TeamMember member : members)
            if (member.getAccount().getIdx().equals(account.getIdx()))
                return true;
        return false;
    }
}
