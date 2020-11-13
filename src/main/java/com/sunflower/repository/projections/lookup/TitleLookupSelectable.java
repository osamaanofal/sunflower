package com.sunflower.repository.projections.lookup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface TitleLookupSelectable {

	@Query("SELECT id as id, title as name FROM #{#entityName} ")
	List<EntityLookup> getEntityLookup();
}
