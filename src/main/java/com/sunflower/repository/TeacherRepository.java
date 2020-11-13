package com.sunflower.repository;

import com.sunflower.domain.TeacherDBEntity;
import com.sunflower.repository.projections.lookup.FirstNameLookupSelectable;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TeacherDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends FirstNameLookupSelectable,BaseEntityRepository<TeacherDBEntity, Long> {
}
