package com.sunflower.service.impl;

import com.sunflower.service.ReceiptService;
import com.sunflower.domain.ReceiptDBEntity;
import com.sunflower.repository.ReceiptRepository;
import com.sunflower.service.dto.ReceiptDTO;
import com.sunflower.service.mapper.ReceiptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReceiptDBEntity}.
 */
@Service
@Transactional
public class ReceiptServiceImpl implements ReceiptService {

    private final Logger log = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }

    @Override
    public ReceiptDTO save(ReceiptDTO receiptDTO) {
        log.debug("Request to save Receipt : {}", receiptDTO);
        ReceiptDBEntity receiptDBEntity = receiptMapper.toEntity(receiptDTO);
        receiptDBEntity = receiptRepository.save(receiptDBEntity);
        return receiptMapper.toDto(receiptDBEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReceiptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Receipts");
        return receiptRepository.findAll(pageable)
            .map(receiptMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReceiptDTO> findOne(Long id) {
        log.debug("Request to get Receipt : {}", id);
        return receiptRepository.findById(id)
            .map(receiptMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Receipt : {}", id);
        receiptRepository.deleteById(id);
    }
}
