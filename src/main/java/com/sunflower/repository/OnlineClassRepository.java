package com.sunflower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunflower.domain.OnlineClassDBEntity;

/**
 * Spring Data  repository for the OnlineClassDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnlineClassRepository extends JpaRepository<OnlineClassDBEntity, Long> {
	
	@Query(value = " Select onlineCourse from OnlineClassDBEntity onlineCourse"
			+ " inner join onlineCourse.course where onlineCourse.status = 'OPEN'  ")
	List<OnlineClassDBEntity> getOpenClassesMappedToCourses();
}
