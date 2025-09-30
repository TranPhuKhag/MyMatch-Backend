package com.mymatch.repository;

import com.mymatch.entity.MaterialItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialItemRepository extends JpaRepository<MaterialItem, Long> {
    List<MaterialItem> findAllByIdInAndMaterialIsNull(List<Long> ids);
}
