package com.sunflower.repository;

import com.sunflower.domain.StudentDBEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the StudentDBEntity entity.
 */
@Repository
public interface StudentRepository extends BaseEntityRepository<StudentDBEntity, Long> {

    @Query(value = "select distinct student from StudentDBEntity student left join fetch student.onlineClasses",
        countQuery = "select count(distinct student) from StudentDBEntity student")
    Page<StudentDBEntity> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct student from StudentDBEntity student left join fetch student.onlineClasses")
    List<StudentDBEntity> findAllWithEagerRelationships();

    @Query("select student from StudentDBEntity student left join fetch student.onlineClasses where student.id =:id")
    Optional<StudentDBEntity> findOneWithEagerRelationships(@Param("id") Long id);
}
