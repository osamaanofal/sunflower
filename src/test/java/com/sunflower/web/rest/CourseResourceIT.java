package com.sunflower.web.rest;

import com.sunflower.SunflowerApp;
import com.sunflower.domain.CourseDBEntity;
import com.sunflower.mapper.CourseMapper;
import com.sunflower.repository.CourseRepository;
import com.sunflower.service.CourseService;
import com.sunflower.service.dto.CourseDTO;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CourseResource} REST controller.
 */
@SpringBootTest(classes = SunflowerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CourseResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCourseMockMvc;

    private CourseDBEntity courseDBEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseDBEntity createEntity(EntityManager em) {
        CourseDBEntity courseDBEntity = new CourseDBEntity()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE);
        return courseDBEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CourseDBEntity createUpdatedEntity(EntityManager em) {
        CourseDBEntity courseDBEntity = new CourseDBEntity()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        return courseDBEntity;
    }

    @BeforeEach
    public void initTest() {
        courseDBEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCourse() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();
        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isCreated());

        // Validate the Course in the database
        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate + 1);
        CourseDBEntity testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCourse.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCourse.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = courseRepository.findAll().size();

        // Create the Course with an existing ID
        courseDBEntity.setId(1L);
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCourseMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        courseDBEntity.setTitle(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);


        restCourseMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        courseDBEntity.setDescription(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);


        restCourseMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseRepository.findAll().size();
        // set the field null
        courseDBEntity.setPrice(null);

        // Create the Course, which fails.
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);


        restCourseMockMvc.perform(post("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourses() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(courseDBEntity);

        // Get all the courseList
        restCourseMockMvc.perform(get("/api/courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(courseDBEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(courseDBEntity);

        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", courseDBEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(courseDBEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCourse() throws Exception {
        // Get the course
        restCourseMockMvc.perform(get("/api/courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(courseDBEntity);

        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Update the course
        CourseDBEntity updatedCourseDBEntity = courseRepository.findById(courseDBEntity.getId()).get();
        // Disconnect from session so that the updates on updatedCourseDBEntity are not directly saved in db
        em.detach(updatedCourseDBEntity);
        updatedCourseDBEntity
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE);
        CourseDTO courseDTO = courseMapper.toDto(updatedCourseDBEntity);

        restCourseMockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isOk());

        // Validate the Course in the database
        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
        CourseDBEntity testCourse = courseList.get(courseList.size() - 1);
        assertThat(testCourse.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCourse.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCourse.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCourse() throws Exception {
        int databaseSizeBeforeUpdate = courseRepository.findAll().size();

        // Create the Course
        CourseDTO courseDTO = courseMapper.toDto(courseDBEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCourseMockMvc.perform(put("/api/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(courseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Course in the database
        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCourse() throws Exception {
        // Initialize the database
        courseRepository.saveAndFlush(courseDBEntity);

        int databaseSizeBeforeDelete = courseRepository.findAll().size();

        // Delete the course
        restCourseMockMvc.perform(delete("/api/courses/{id}", courseDBEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CourseDBEntity> courseList = courseRepository.findAll();
        assertThat(courseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
