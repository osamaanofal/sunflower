package com.sunflower.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sunflower.repository.projections.lookup.EntityLookup;
import com.sunflower.service.dto.BaseEntityDTO;

public interface EntityService<D extends BaseEntityDTO, E> {

	/**
	 * Save a Entity.
	 *
	 * @param EntityDTO the entity to save.
	 * @return the persisted entity.
	 */
	D save(D EntityDTO);

	/**
	 * Get all the Entitys.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<D> findAll(Pageable pageable);

	/**
	 * Get the "id" Entity.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<D> findOne(Long id);

	/**
	 * Delete the "id" Entity.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);
	
	List<EntityLookup> getEntityLookups();

}
