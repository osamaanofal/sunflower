package com.sunflower.web.rest.delegat.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.domain.StudentDBEntity;
import com.sunflower.mapper.OnlineClassMapper;
import com.sunflower.repository.OnlineClassRepository;
import com.sunflower.repository.StudentRepository;
import com.sunflower.web.rest.StudentApiDelegate;
import com.sunflower.web.rest.model.StudentClass;


@Service
public class StudentApiDelegateImpl implements StudentApiDelegate {

	StudentRepository studentRepository;

	OnlineClassMapper onlineClassMapper;

	@Autowired
	OnlineClassRepository onlineClassRepository;
	
	public StudentApiDelegateImpl(StudentRepository studentRepository,OnlineClassMapper onlineClassMapper) {
		super();
		this.studentRepository = studentRepository;
		this.onlineClassMapper = onlineClassMapper;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<StudentClass>> getStudentClasses(Long studentId) {
		StudentDBEntity student = studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("Student Not Found"));
		
		List<StudentClass> studentClasses = student.getOnlineClasses().stream().map(onlineClassMapper::toStudentClass)
			.collect(Collectors.toList());
		
		return new ResponseEntity<List<StudentClass>>(studentClasses, HttpStatus.OK);
	}
	
	
	@Override
	@Transactional
	public ResponseEntity<Boolean> registerStudentToClass(Long studentId, Long classId) {
		StudentDBEntity student = studentRepository.findById(studentId)
				.orElseThrow(()-> new RuntimeException("Student Not Found"));
		
		OnlineClassDBEntity onlineClassDBEntity = onlineClassRepository.findById(classId)
				.orElseThrow(()-> new RuntimeException("OnlineClass Not Found"));
		
		if(student.getOnlineClasses().size() >= 3) {
			throw new RuntimeException(" student can't has more than 3 courses/classes / term ");
		}
		
		student.addOnlineClass(onlineClassDBEntity);
		
		studentRepository.save(student);
		
		
		return ResponseEntity.ok(true);
	}
}
