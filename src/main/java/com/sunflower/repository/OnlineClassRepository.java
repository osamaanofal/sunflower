package com.sunflower.repository;

import com.sunflower.domain.OnlineClassDBEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OnlineClassDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnlineClassRepository extends JpaRepository<OnlineClassDBEntity, Long> {
}
