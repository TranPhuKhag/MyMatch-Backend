package com.mymatch.repository;

import com.mymatch.entity.MaterialItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialItemRepository extends JpaRepository<MaterialItem, Long> {
}
