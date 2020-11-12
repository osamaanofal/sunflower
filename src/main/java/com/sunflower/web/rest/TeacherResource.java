package com.sunflower.web.rest;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunflower.domain.TeacherDBEntity;
import com.sunflower.service.TeacherService;
import com.sunflower.service.dto.TeacherDTO;

/**
 * REST controller for managing {@link com.sunflower.domain.TeacherDBEntity}.
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherResource extends BaseEntityResource<TeacherDTO, TeacherDBEntity> {

	private final TeacherService teacherService;

	public TeacherResource(TeacherService teacherService) {
		super(teacherService, LoggerFactory.getLogger(TeacherResource.class));
		this.teacherService = teacherService;
	}
}
