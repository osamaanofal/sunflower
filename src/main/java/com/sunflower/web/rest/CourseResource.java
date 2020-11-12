package com.sunflower.web.rest;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunflower.domain.CourseDBEntity;
import com.sunflower.service.CourseService;
import com.sunflower.service.dto.CourseDTO;

/**
 * REST controller for managing {@link com.sunflower.domain.CourseDBEntity}.
 */
@RestController
@RequestMapping("api/courses")
public class CourseResource extends BaseEntityResource<CourseDTO, CourseDBEntity> {

	private final CourseService courseService;

	public CourseResource(CourseService courseService) {
		super(courseService, LoggerFactory.getLogger(CourseResource.class));
		this.courseService = courseService;
	}

	public CourseService getCourseService() {
		return courseService;
	}

	@GetMapping("/hello")
	public String get() {
		return "ghello";
	}
}
