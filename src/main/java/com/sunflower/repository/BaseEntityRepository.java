package com.sunflower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.sunflower.repository.projections.lookup.EntityLookup;

@NoRepositoryBean
public interface BaseEntityRepository<T,ID> extends JpaRepository<T, ID> {
	
	List<EntityLookup> getEntityLookup();
}
