package com.sunflower.web.rest;

import com.sunflower.SunflowerApp;
import com.sunflower.domain.TeacherDBEntity;
import com.sunflower.mapper.TeacherMapper;
import com.sunflower.repository.TeacherRepository;
import com.sunflower.service.TeacherService;
import com.sunflower.service.dto.TeacherDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TeacherResource} REST controller.
 */
@SpringBootTest(classes = SunflowerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TeacherResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTeacherMockMvc;

    private TeacherDBEntity teacherDBEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeacherDBEntity createEntity(EntityManager em) {
        TeacherDBEntity teacherDBEntity = new TeacherDBEntity()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME);
        return teacherDBEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeacherDBEntity createUpdatedEntity(EntityManager em) {
        TeacherDBEntity teacherDBEntity = new TeacherDBEntity()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        return teacherDBEntity;
    }

    @BeforeEach
    public void initTest() {
        teacherDBEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();
        // Create the Teacher
        TeacherDTO teacherDTO = teacherMapper.toDto(teacherDBEntity);
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1);
        TeacherDBEntity testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
    }

    @Test
    @Transactional
    public void createTeacherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher with an existing ID
        teacherDBEntity.setId(1L);
        TeacherDTO teacherDTO = teacherMapper.toDto(teacherDBEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacherDBEntity.setFirstName(null);

        // Create the Teacher, which fails.
        TeacherDTO teacherDTO = teacherMapper.toDto(teacherDBEntity);


        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest());

        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = teacherRepository.findAll().size();
        // set the field null
        teacherDBEntity.setLastName(null);

        // Create the Teacher, which fails.
        TeacherDTO teacherDTO = teacherMapper.toDto(teacherDBEntity);


        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest());

        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacherDBEntity);

        // Get all the teacherList
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacherDBEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)));
    }
    
    @Test
    @Transactional
    public void getTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacherDBEntity);

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacherDBEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teacherDBEntity.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacherDBEntity);

        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Update the teacher
        TeacherDBEntity updatedTeacherDBEntity = teacherRepository.findById(teacherDBEntity.getId()).get();
        // Disconnect from session so that the updates on updatedTeacherDBEntity are not directly saved in db
        em.detach(updatedTeacherDBEntity);
        updatedTeacherDBEntity
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME);
        TeacherDTO teacherDTO = teacherMapper.toDto(updatedTeacherDBEntity);

        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        TeacherDBEntity testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Create the Teacher
        TeacherDTO teacherDTO = teacherMapper.toDto(teacherDBEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teacherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacherDBEntity);

        int databaseSizeBeforeDelete = teacherRepository.findAll().size();

        // Delete the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacherDBEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeacherDBEntity> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
