package com.sunflower.repository;

import com.sunflower.domain.CourseDBEntity;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CourseDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<CourseDBEntity, Long> {

	@Query(value = " Select course from CourseDBEntity course inner join course.onlineClasses cls where cls.status = 'OPEN' ")
	List<CourseDBEntity> getOpenCoursesMappedToClasses();

}

