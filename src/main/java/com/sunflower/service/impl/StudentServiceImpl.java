package com.sunflower.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.StudentDBEntity;
import com.sunflower.repository.StudentRepository;
import com.sunflower.service.StudentService;
import com.sunflower.service.dto.StudentDTO;
import com.sunflower.service.mapper.StudentMapper;

/**
 * Service Implementation for managing {@link StudentDBEntity}.
 */
@Service
@Transactional
public class StudentServiceImpl extends BaseEntityServiceImpl<StudentDTO, StudentDBEntity> implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
    	super(studentRepository, studentMapper, LoggerFactory.getLogger(StudentServiceImpl.class));
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

}
