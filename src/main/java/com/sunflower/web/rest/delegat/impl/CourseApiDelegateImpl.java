package com.sunflower.web.rest.delegat.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.mapper.OnlineClassMapper;
import com.sunflower.service.CourseService;
import com.sunflower.service.OnlineClassService;
import com.sunflower.web.rest.CoursesApiDelegate;
import com.sunflower.web.rest.model.AvailableCourse;

@Service
public class CourseApiDelegateImpl implements CoursesApiDelegate {

	CourseService courseService;

	OnlineClassService onlineClassService;

	@Autowired
	OnlineClassMapper availableCourseMapper;

	public CourseApiDelegateImpl(CourseService courseService, OnlineClassService onlineClassService) {
		super();
		this.courseService = courseService;
		this.onlineClassService = onlineClassService;
	}

	/**
	 * 
	 * list all courses which have open classes 
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<AvailableCourse>> getOpenCourses() {
		List<OnlineClassDBEntity> onlineClassDBEntities = onlineClassService.getOpenClassesMappedToCourses();

		List<AvailableCourse> availableCourses = onlineClassDBEntities.stream()
				.map(availableCourseMapper::toAvailableCourseModel).collect(Collectors.toList());

		return new ResponseEntity<List<AvailableCourse>>(availableCourses, HttpStatus.OK);
	}
}
