package com.sunflower.web.rest;

import com.sunflower.service.OnlineClassService;
import com.sunflower.web.rest.errors.BadRequestAlertException;
import com.sunflower.service.dto.OnlineClassDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sunflower.domain.OnlineClassDBEntity}.
 */
@RestController
@RequestMapping("/api")
public class OnlineClassResource {

    private final Logger log = LoggerFactory.getLogger(OnlineClassResource.class);

    private static final String ENTITY_NAME = "sunflowerOnlineClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnlineClassService onlineClassService;

    public OnlineClassResource(OnlineClassService onlineClassService) {
        this.onlineClassService = onlineClassService;
    }

    /**
     * {@code POST  /online-classes} : Create a new onlineClass.
     *
     * @param onlineClassDTO the onlineClassDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onlineClassDTO, or with status {@code 400 (Bad Request)} if the onlineClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/online-classes")
    public ResponseEntity<OnlineClassDTO> createOnlineClass(@Valid @RequestBody OnlineClassDTO onlineClassDTO) throws URISyntaxException {
        log.debug("REST request to save OnlineClass : {}", onlineClassDTO);
        if (onlineClassDTO.getId() != null) {
            throw new BadRequestAlertException("A new onlineClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OnlineClassDTO result = onlineClassService.save(onlineClassDTO);
        return ResponseEntity.created(new URI("/api/online-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /online-classes} : Updates an existing onlineClass.
     *
     * @param onlineClassDTO the onlineClassDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onlineClassDTO,
     * or with status {@code 400 (Bad Request)} if the onlineClassDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onlineClassDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/online-classes")
    public ResponseEntity<OnlineClassDTO> updateOnlineClass(@Valid @RequestBody OnlineClassDTO onlineClassDTO) throws URISyntaxException {
        log.debug("REST request to update OnlineClass : {}", onlineClassDTO);
        if (onlineClassDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OnlineClassDTO result = onlineClassService.save(onlineClassDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, onlineClassDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /online-classes} : get all the onlineClasses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onlineClasses in body.
     */
    @GetMapping("/online-classes")
    public ResponseEntity<List<OnlineClassDTO>> getAllOnlineClasses(Pageable pageable) {
        log.debug("REST request to get a page of OnlineClasses");
        Page<OnlineClassDTO> page = onlineClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /online-classes/:id} : get the "id" onlineClass.
     *
     * @param id the id of the onlineClassDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onlineClassDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/online-classes/{id}")
    public ResponseEntity<OnlineClassDTO> getOnlineClass(@PathVariable Long id) {
        log.debug("REST request to get OnlineClass : {}", id);
        Optional<OnlineClassDTO> onlineClassDTO = onlineClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(onlineClassDTO);
    }

    /**
     * {@code DELETE  /online-classes/:id} : delete the "id" onlineClass.
     *
     * @param id the id of the onlineClassDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/online-classes/{id}")
    public ResponseEntity<Void> deleteOnlineClass(@PathVariable Long id) {
        log.debug("REST request to delete OnlineClass : {}", id);
        onlineClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
