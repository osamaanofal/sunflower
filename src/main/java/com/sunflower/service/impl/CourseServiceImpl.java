package com.sunflower.service.impl;

import com.sunflower.service.CourseService;
import com.sunflower.domain.CourseDBEntity;
import com.sunflower.repository.CourseRepository;
import com.sunflower.service.dto.CourseDTO;
import com.sunflower.service.mapper.CourseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseDBEntity}.
 */
@Service
@Transactional
public class CourseServiceImpl extends BaseEntityServiceImpl<CourseDTO, CourseDBEntity> implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
    	super(courseRepository, courseMapper, LoggerFactory.getLogger(CourseServiceImpl.class));
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

	@Override
	@Transactional(readOnly = true)
	public List<CourseDBEntity> getOpenCoursesMappedToClasses() {
        log.debug("Request to getOpenClassesMappedToCourses");
        
        List<CourseDBEntity> courseDBEntities = courseRepository.getOpenCoursesMappedToClasses();
                
		return courseDBEntities;
	}
}
