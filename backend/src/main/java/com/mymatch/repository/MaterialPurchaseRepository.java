package com.mymatch.repository;

import com.mymatch.entity.MaterialPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MaterialPurchaseRepository extends JpaRepository<MaterialPurchase, Long>, JpaSpecificationExecutor<MaterialPurchase> {
    boolean existsByMaterial_IdAndAndBuyer_Id(Long materialId, Long userId);

    int countByMaterialId(Long materialId);
}