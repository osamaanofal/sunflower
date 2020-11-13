package com.sunflower.repository.projections.lookup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface FirstNameLookupSelectable {

	@Query("SELECT id as id, firstName as name FROM #{#entityName} ")
	 List<EntityLookup> getEntityLookup();
}
