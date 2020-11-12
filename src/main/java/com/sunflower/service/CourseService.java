package com.sunflower.service;

import com.sunflower.domain.CourseDBEntity;
import com.sunflower.service.dto.CourseDTO;
import com.sunflower.web.rest.model.AvailableCourse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sunflower.domain.CourseDBEntity}.
 */
public interface CourseService {

    /**
     * Save a course.
     *
     * @param courseDTO the entity to save.
     * @return the persisted entity.
     */
    CourseDTO save(CourseDTO courseDTO);

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" course.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseDTO> findOne(Long id);

    /**
     * Delete the "id" course.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * get open courses mapped to class
     *
     * 
     */
    List<CourseDBEntity> getOpenCoursesMappedToClasses();
}
