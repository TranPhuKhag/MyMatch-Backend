package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE material SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Material extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String originalFileName;

    @Column(nullable = false)
    String fileType;

    @Column(length = 1000)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecturer_id")
    Lecturer lecturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    Course course;

    String fileURL;

    Long price; // coin

    Double size;
    @Builder.Default
    int downloadCont = 0;

    @Builder.Default
    int purchaseCount = 0;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    List<MaterialPurchase> purchases;
}
