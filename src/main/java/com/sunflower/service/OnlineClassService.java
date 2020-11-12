package com.sunflower.service;

import com.sunflower.domain.CourseDBEntity;
import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.service.dto.OnlineClassDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sunflower.domain.OnlineClassDBEntity}.
 */
public interface OnlineClassService {

    /**
     * Save a onlineClass.
     *
     * @param onlineClassDTO the entity to save.
     * @return the persisted entity.
     */
    OnlineClassDTO save(OnlineClassDTO onlineClassDTO);

    /**
     * Get all the onlineClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OnlineClassDTO> findAll(Pageable pageable);


    /**
     * Get the "id" onlineClass.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OnlineClassDTO> findOne(Long id);

    /**
     * Delete the "id" onlineClass.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * closeOnlineClass
     *
     * @param classId the id of the entity.
     * @return Boolean
     */
    Boolean closeOnlineClass(Long classId) throws Exception;
    
    
    /**
     * availOnlineClass
     *
     * @param classId the id of the entity.
     * @return Boolean
     */
    Boolean availOnlineClass(Long classId) throws Exception;
    
    /**
     * get open courses mapped to class
     *
     * 
     */
    List<OnlineClassDBEntity> getOpenClassesMappedToCourses();
}
