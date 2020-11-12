package com.sunflower.service;

import com.sunflower.service.dto.ReceiptDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sunflower.domain.ReceiptDBEntity}.
 */
public interface ReceiptService {

    /**
     * Save a receipt.
     *
     * @param receiptDTO the entity to save.
     * @return the persisted entity.
     */
    ReceiptDTO save(ReceiptDTO receiptDTO);

    /**
     * Get all the receipts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReceiptDTO> findAll(Pageable pageable);


    /**
     * Get the "id" receipt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReceiptDTO> findOne(Long id);

    /**
     * Delete the "id" receipt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
