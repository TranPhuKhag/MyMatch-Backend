package com.mymatch.entity;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.mymatch.common.AbstractAuditingEntity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE user SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100, unique = true)
    String username;

    @Column(length = 100, unique = true)
    String email;

    @Column(length = 100)
    String password;

    @Builder.Default
    Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;

    String firstName;
    String lastName;
    String address;
    String phone;
    @Column(name = "avatar_url", length = 2048)
    String avatarUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    Wallet wallet;
}
