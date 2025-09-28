package com.mymatch.entity;

import com.mymatch.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE material_item SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class MaterialItem extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;
    private String fileURL;
    Double size;
    @Column(nullable = false)
    String originalFileName;
    @Column(nullable = false)
    String fileType;
    @Builder.Default
    int downloadCont = 0;
}