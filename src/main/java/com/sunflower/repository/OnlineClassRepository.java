package com.sunflower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.repository.projections.lookup.EntityLookup;
import com.sunflower.repository.projections.lookup.TitleLookupSelectable;

/**
 * Spring Data repository for the OnlineClassDBEntity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OnlineClassRepository extends TitleLookupSelectable, BaseEntityRepository<OnlineClassDBEntity, Long> {

	@Query(value = " Select onlineCourse from OnlineClassDBEntity onlineCourse"
			+ " inner join onlineCourse.course where onlineCourse.status = 'OPEN'  ")
	List<OnlineClassDBEntity> getOpenClassesMappedToCourses();

	@Query("SELECT e.id as id, e.title ||' / ' || c.title  as name FROM OnlineClassDBEntity e Inner Join e.course c where e.status = 'OPEN'")
	@Override
	List<EntityLookup> getEntityLookup();

}
