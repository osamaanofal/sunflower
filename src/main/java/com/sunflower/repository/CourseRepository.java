package com.sunflower.repository;

import com.sunflower.domain.CourseDBEntity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CourseDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<CourseDBEntity, Long> {
}
