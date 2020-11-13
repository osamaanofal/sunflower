package com.sunflower.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.TeacherDBEntity;
import com.sunflower.repository.TeacherRepository;
import com.sunflower.service.TeacherService;
import com.sunflower.service.dto.TeacherDTO;
import com.sunflower.service.mapper.TeacherMapper;

/**
 * Service Implementation for managing {@link TeacherDBEntity}.
 */
@Service
@Transactional
public class TeacherServiceImpl extends BaseEntityServiceImpl<TeacherDTO, TeacherDBEntity> implements TeacherService {

	private final TeacherRepository teacherRepository;

	private final TeacherMapper teacherMapper;

	public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
		super(teacherRepository, teacherMapper, LoggerFactory.getLogger(TeacherServiceImpl.class));
		this.teacherRepository = teacherRepository;
		this.teacherMapper = teacherMapper;
	}

}
