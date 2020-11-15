package com.sunflower.web.rest;

import com.sunflower.SunflowerApp;
import com.sunflower.domain.StudentDBEntity;
import com.sunflower.mapper.StudentMapper;
import com.sunflower.repository.StudentRepository;
import com.sunflower.service.StudentService;
import com.sunflower.service.dto.StudentDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = SunflowerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;

    @Mock
    private StudentRepository studentRepositoryMock;

    @Autowired
    private StudentMapper studentMapper;

    @Mock
    private StudentService studentServiceMock;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private StudentDBEntity studentDBEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentDBEntity createEntity(EntityManager em) {
        StudentDBEntity studentDBEntity = new StudentDBEntity()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return studentDBEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudentDBEntity createUpdatedEntity(EntityManager em) {
        StudentDBEntity studentDBEntity = new StudentDBEntity()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return studentDBEntity;
    }

    @BeforeEach
    public void initTest() {
        studentDBEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(studentDBEntity);
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        StudentDBEntity testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        studentDBEntity.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(studentDBEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        studentDBEntity.setFirstName(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(studentDBEntity);


        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        studentDBEntity.setLastName(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(studentDBEntity);


        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(studentDBEntity);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentDBEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(studentDBEntity);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", studentDBEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(studentDBEntity.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(studentDBEntity);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        StudentDBEntity updatedStudentDBEntity = studentRepository.findById(studentDBEntity.getId()).get();
        // Disconnect from session so that the updates on updatedStudentDBEntity are not directly saved in db
        em.detach(updatedStudentDBEntity);
        updatedStudentDBEntity
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudentDBEntity);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        StudentDBEntity testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(studentDBEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(studentDBEntity);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", studentDBEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StudentDBEntity> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
