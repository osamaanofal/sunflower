package com.sunflower.web.rest;

import com.sunflower.SunflowerApp;
import com.sunflower.domain.ReceiptDBEntity;
import com.sunflower.mapper.ReceiptMapper;
import com.sunflower.repository.ReceiptRepository;
import com.sunflower.service.ReceiptService;
import com.sunflower.service.dto.ReceiptDTO;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ReceiptResource} REST controller.
 */
@SpringBootTest(classes = SunflowerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ReceiptResourceIT {

    private static final String DEFAULT_RECEIPT_NO = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_NO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIPT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIPT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ReceiptMapper receiptMapper;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReceiptMockMvc;

    private ReceiptDBEntity receiptDBEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptDBEntity createEntity(EntityManager em) {
        ReceiptDBEntity receiptDBEntity = new ReceiptDBEntity()
            .receiptNo(DEFAULT_RECEIPT_NO)
            .receiptDate(DEFAULT_RECEIPT_DATE)
            .amount(DEFAULT_AMOUNT)
            .description(DEFAULT_DESCRIPTION);
        return receiptDBEntity;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReceiptDBEntity createUpdatedEntity(EntityManager em) {
        ReceiptDBEntity receiptDBEntity = new ReceiptDBEntity()
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION);
        return receiptDBEntity;
    }

    @BeforeEach
    public void initTest() {
        receiptDBEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createReceipt() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();
        // Create the Receipt
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);
        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isCreated());

        // Validate the Receipt in the database
        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate + 1);
        ReceiptDBEntity testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getReceiptNo()).isEqualTo(DEFAULT_RECEIPT_NO);
        assertThat(testReceipt.getReceiptDate()).isEqualTo(DEFAULT_RECEIPT_DATE);
        assertThat(testReceipt.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testReceipt.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createReceiptWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = receiptRepository.findAll().size();

        // Create the Receipt with an existing ID
        receiptDBEntity.setId(1L);
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receipt in the database
        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReceiptNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receiptDBEntity.setReceiptNo(null);

        // Create the Receipt, which fails.
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);


        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReceiptDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receiptDBEntity.setReceiptDate(null);

        // Create the Receipt, which fails.
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);


        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = receiptRepository.findAll().size();
        // set the field null
        receiptDBEntity.setAmount(null);

        // Create the Receipt, which fails.
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);


        restReceiptMockMvc.perform(post("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReceipts() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receiptDBEntity);

        // Get all the receiptList
        restReceiptMockMvc.perform(get("/api/receipts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(receiptDBEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiptNo").value(hasItem(DEFAULT_RECEIPT_NO)))
            .andExpect(jsonPath("$.[*].receiptDate").value(hasItem(DEFAULT_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receiptDBEntity);

        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", receiptDBEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(receiptDBEntity.getId().intValue()))
            .andExpect(jsonPath("$.receiptNo").value(DEFAULT_RECEIPT_NO))
            .andExpect(jsonPath("$.receiptDate").value(DEFAULT_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingReceipt() throws Exception {
        // Get the receipt
        restReceiptMockMvc.perform(get("/api/receipts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receiptDBEntity);

        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Update the receipt
        ReceiptDBEntity updatedReceiptDBEntity = receiptRepository.findById(receiptDBEntity.getId()).get();
        // Disconnect from session so that the updates on updatedReceiptDBEntity are not directly saved in db
        em.detach(updatedReceiptDBEntity);
        updatedReceiptDBEntity
            .receiptNo(UPDATED_RECEIPT_NO)
            .receiptDate(UPDATED_RECEIPT_DATE)
            .amount(UPDATED_AMOUNT)
            .description(UPDATED_DESCRIPTION);
        ReceiptDTO receiptDTO = receiptMapper.toDto(updatedReceiptDBEntity);

        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isOk());

        // Validate the Receipt in the database
        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate);
        ReceiptDBEntity testReceipt = receiptList.get(receiptList.size() - 1);
        assertThat(testReceipt.getReceiptNo()).isEqualTo(UPDATED_RECEIPT_NO);
        assertThat(testReceipt.getReceiptDate()).isEqualTo(UPDATED_RECEIPT_DATE);
        assertThat(testReceipt.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testReceipt.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingReceipt() throws Exception {
        int databaseSizeBeforeUpdate = receiptRepository.findAll().size();

        // Create the Receipt
        ReceiptDTO receiptDTO = receiptMapper.toDto(receiptDBEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReceiptMockMvc.perform(put("/api/receipts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(receiptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Receipt in the database
        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReceipt() throws Exception {
        // Initialize the database
        receiptRepository.saveAndFlush(receiptDBEntity);

        int databaseSizeBeforeDelete = receiptRepository.findAll().size();

        // Delete the receipt
        restReceiptMockMvc.perform(delete("/api/receipts/{id}", receiptDBEntity.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReceiptDBEntity> receiptList = receiptRepository.findAll();
        assertThat(receiptList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
