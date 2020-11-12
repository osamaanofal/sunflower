package com.sunflower.web.rest;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunflower.domain.StudentDBEntity;
import com.sunflower.service.StudentService;
import com.sunflower.service.dto.StudentDTO;

/**
 * REST controller for managing {@link com.sunflower.domain.StudentDBEntity}.
 */
@RestController
@RequestMapping("/api/students")
public class StudentResource extends BaseEntityResource<StudentDTO, StudentDBEntity> {

	private final StudentService studentService;

	public StudentResource(StudentService studentService) {
		super(studentService, LoggerFactory.getLogger(StudentResource.class));
		this.studentService = studentService;
	}
}
