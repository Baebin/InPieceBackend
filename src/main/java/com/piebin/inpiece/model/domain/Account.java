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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // Authorization
    @Column(unique = true)
    private String id;

    private String password;

    // Etc
    private String name;

    private String phone;

    private String email;

    private String description;

    // University
    private String major;

    @Column(name = "student_id")
    private String studentId;

    // OAuth2
    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    // Etc
    @CreatedDate
    @Column(name = "reg_date")
    private LocalDateTime regDate;
}
