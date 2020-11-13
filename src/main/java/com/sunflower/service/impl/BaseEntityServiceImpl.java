package com.sunflower.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.repository.BaseEntityRepository;
import com.sunflower.repository.projections.lookup.EntityLookup;
import com.sunflower.service.EntityService;
import com.sunflower.service.dto.BaseEntityDTO;
import com.sunflower.service.mapper.EntityMapper;

public abstract class BaseEntityServiceImpl<D extends BaseEntityDTO, E> implements EntityService<D, E> {

	protected final Logger log;

	private final BaseEntityRepository<E, Long> baseEntityRepository;

	private final EntityMapper<D, E> entityMapper;

	public BaseEntityServiceImpl(BaseEntityRepository<E, Long> baseEntityRepository, EntityMapper<D, E> entityMapper,
			Logger log) {
		this.baseEntityRepository = baseEntityRepository;
		this.entityMapper = entityMapper;
		this.log = log;
	}

	@Override
	public D save(D courseDTO) {
		log.debug("Request to save Entity : {}", courseDTO);
		E courseDBEntity = entityMapper.toEntity(courseDTO);
		courseDBEntity = baseEntityRepository.save(courseDBEntity);
		return entityMapper.toDto(courseDBEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<D> findAll(Pageable pageable) {
		log.debug("Request to get all Entities");
		return baseEntityRepository.findAll(pageable).map(entityMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<D> findOne(Long id) {
		log.debug("Request to get Entity : {}", id);
		return baseEntityRepository.findById(id).map(entityMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Entity : {}", id);
		baseEntityRepository.deleteById(id);
	}

	@Override
	public List<EntityLookup> getEntityLookups() {
		log.debug("Request to Entity lookups");

		return baseEntityRepository.getEntityLookup();
	}
}
