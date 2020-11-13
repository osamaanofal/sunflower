package com.sunflower.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sunflower.repository.projections.lookup.EntityLookup;
import com.sunflower.service.EntityService;
import com.sunflower.service.dto.BaseEntityDTO;
import com.sunflower.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

public abstract class BaseEntityResource<D extends BaseEntityDTO, E> {

	protected final Logger log;

	protected static final String ENTITY_NAME = "sunflowerEntity";

	@Value("${jhipster.clientApp.name}")
	protected String applicationName;

	@Autowired
	private ServletContext context;

	private EntityService<D, E> entityService;

	public BaseEntityResource(EntityService<D, E> entityService, Logger log) {
		super();
		this.log = log;
		this.entityService = entityService;
	}

	// *************************************************************

	/**
	 * {@code POST  /entities} : Create a new entity.
	 *
	 * @param entityDTO the entityDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new entityDTO, or with status {@code 400 (Bad Request)} if
	 *         the entity has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping()
	public ResponseEntity<D> createEntity(@Valid @RequestBody D entityDTO) throws URISyntaxException {
		log.debug("REST request to save Entity : {}", entityDTO);
		if (entityDTO.getId() != null) {
			throw new BadRequestAlertException("A new entity cannot already have an ID", ENTITY_NAME, "idexists");
		}
		D result = entityService.save(entityDTO);
		return ResponseEntity
				.created(new URI(context.getContextPath() + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /entitys} : Updates an existing entity.
	 *
	 * @param entityDTO the entityDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated entityDTO, or with status {@code 400 (Bad Request)} if
	 *         the entityDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the entityDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping()
	public ResponseEntity<D> updateEntity(@Valid @RequestBody D entityDTO) throws URISyntaxException {
		log.debug("REST request to update Entity : {}", entityDTO);
		if (entityDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		D result = entityService.save(entityDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /entitys} : get all the entitys.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of entitys in body.
	 */
	@GetMapping()
	public ResponseEntity<List<D>> getAllEntitys(Pageable pageable) {
		log.debug("REST request to get a page of Entitys");
		Page<D> page = entityService.findAll(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /entitys/:id} : get the "id" entity.
	 *
	 * @param id the id of the entityDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the entityDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/{id}")
	public ResponseEntity<D> getEntity(@PathVariable Long id) {
		log.debug("REST request to get Entity : {}", id);
		Optional<D> entityDTO = entityService.findOne(id);
		return ResponseUtil.wrapOrNotFound(entityDTO);
	}

	/**
	 * {@code DELETE  /entitys/:id} : delete the "id" entity.
	 *
	 * @param id the id of the entityDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
		log.debug("REST request to delete Entity : {}", id);
		entityService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	
	/**
	 * {@code GET  /lookups} 
	 *
	 *
	 * 
	 */
	@GetMapping("/lookups")
	public ResponseEntity<List<EntityLookup>> entityLookups() {
		log.debug("REST request to Entity lookups ");
		return ResponseEntity.ok().body(entityService.getEntityLookups());
	}
}
