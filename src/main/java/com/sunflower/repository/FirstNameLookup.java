package com.sunflower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sunflower.repository.projections.lookup.EntityLookup;

public interface FirstNameLookup {

	@Query("SELECT id as id, firstName as title FROM #{#entityName} ")

	 List<EntityLookup> getEntityLookup();
}
