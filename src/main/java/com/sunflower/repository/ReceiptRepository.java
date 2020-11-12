package com.sunflower.repository;

import com.sunflower.domain.ReceiptDBEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ReceiptDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptDBEntity, Long> {
}
