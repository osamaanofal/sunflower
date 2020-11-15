package com.sunflower.web.rest;

import com.sunflower.SunflowerApp;
import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.repository.OnlineClassRepository;
import com.sunflower.service.OnlineClassService;
import com.sunflower.service.dto.OnlineClassDTO;

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

import com.sunflower.domain.enumeration.Status;
import com.sunflower.mapper.OnlineClassMapper;
/**
 * Integration tests for the {@link OnlineClassResource} REST controller.
 */
@SpringBootTest(classes = SunflowerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OnlineClassResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.CLOSED;

    @Autowired
    private OnlineClassRepository onlineClassRepository;

    @Autowired
    private OnlineClassMapper onlineClassMapper;

    @Autowired
    private OnlineClassService onlineClassService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOnlineClassMockMvc;

    private OnlineClassDBEntity onlineClassDBEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnlineClassDBEntity createEntity(EntityManager em) {
        OnlineClassDBEntity onlineClassDBEntity = new OnlineClassDBEntity()
            .title(DEFAULT_TITLE)
            .status(DEFAULT_STATUS);
        return onlineClassDBEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OnlineClassDBEntity createUpdatedEntity(EntityManager em) {
        OnlineClassDBEntity onlineClassDBEntity = new OnlineClassDBEntity()
            .title(UPDATED_TITLE)
            .status(UPDATED_STATUS);
        return onlineClassDBEntity;
    }

    @BeforeEach
    public void initTest() {
        onlineClassDBEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createOnlineClass() throws Exception {
        int databaseSizeBeforeCreate = onlineClassRepository.findAll().size();
        // Create the OnlineClass
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(onlineClassDBEntity);
        restOnlineClassMockMvc.perform(post("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isCreated());

        // Validate the OnlineClass in the database
        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeCreate + 1);
        OnlineClassDBEntity testOnlineClass = onlineClassList.get(onlineClassList.size() - 1);
        assertThat(testOnlineClass.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOnlineClass.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createOnlineClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = onlineClassRepository.findAll().size();

        // Create the OnlineClass with an existing ID
        onlineClassDBEntity.setId(1L);
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(onlineClassDBEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOnlineClassMockMvc.perform(post("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OnlineClass in the database
        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineClassRepository.findAll().size();
        // set the field null
        onlineClassDBEntity.setTitle(null);

        // Create the OnlineClass, which fails.
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(onlineClassDBEntity);


        restOnlineClassMockMvc.perform(post("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isBadRequest());

        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = onlineClassRepository.findAll().size();
        // set the field null
        onlineClassDBEntity.setStatus(null);

        // Create the OnlineClass, which fails.
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(onlineClassDBEntity);


        restOnlineClassMockMvc.perform(post("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isBadRequest());

        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOnlineClasses() throws Exception {
        // Initialize the database
        onlineClassRepository.saveAndFlush(onlineClassDBEntity);

        // Get all the onlineClassList
        restOnlineClassMockMvc.perform(get("/api/online-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(onlineClassDBEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getOnlineClass() throws Exception {
        // Initialize the database
        onlineClassRepository.saveAndFlush(onlineClassDBEntity);

        // Get the onlineClass
        restOnlineClassMockMvc.perform(get("/api/online-classes/{id}", onlineClassDBEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(onlineClassDBEntity.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingOnlineClass() throws Exception {
        // Get the onlineClass
        restOnlineClassMockMvc.perform(get("/api/online-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOnlineClass() throws Exception {
        // Initialize the database
        onlineClassRepository.saveAndFlush(onlineClassDBEntity);

        int databaseSizeBeforeUpdate = onlineClassRepository.findAll().size();

        // Update the onlineClass
        OnlineClassDBEntity updatedOnlineClassDBEntity = onlineClassRepository.findById(onlineClassDBEntity.getId()).get();
        // Disconnect from session so that the updates on updatedOnlineClassDBEntity are not directly saved in db
        em.detach(updatedOnlineClassDBEntity);
        updatedOnlineClassDBEntity
            .title(UPDATED_TITLE)
            .status(UPDATED_STATUS);
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(updatedOnlineClassDBEntity);

        restOnlineClassMockMvc.perform(put("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isOk());

        // Validate the OnlineClass in the database
        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeUpdate);
        OnlineClassDBEntity testOnlineClass = onlineClassList.get(onlineClassList.size() - 1);
        assertThat(testOnlineClass.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOnlineClass.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingOnlineClass() throws Exception {
        int databaseSizeBeforeUpdate = onlineClassRepository.findAll().size();

        // Create the OnlineClass
        OnlineClassDTO onlineClassDTO = onlineClassMapper.toDto(onlineClassDBEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOnlineClassMockMvc.perform(put("/api/online-classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(onlineClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OnlineClass in the database
        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOnlineClass() throws Exception {
        // Initialize the database
        onlineClassRepository.saveAndFlush(onlineClassDBEntity);

        int databaseSizeBeforeDelete = onlineClassRepository.findAll().size();

        // Delete the onlineClass
        restOnlineClassMockMvc.perform(delete("/api/online-classes/{id}", onlineClassDBEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OnlineClassDBEntity> onlineClassList = onlineClassRepository.findAll();
        assertThat(onlineClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
