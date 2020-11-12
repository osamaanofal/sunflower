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
public interface CourseService extends EntityService<CourseDTO, CourseDBEntity> {


    List<CourseDBEntity> getOpenCoursesMappedToClasses();
}
