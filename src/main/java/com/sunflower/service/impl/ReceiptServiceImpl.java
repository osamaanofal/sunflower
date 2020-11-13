package com.sunflower.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.ReceiptDBEntity;
import com.sunflower.repository.ReceiptRepository;
import com.sunflower.service.ReceiptService;
import com.sunflower.service.dto.ReceiptDTO;
import com.sunflower.service.mapper.ReceiptMapper;

/**
 * Service Implementation for managing {@link ReceiptDBEntity}.
 */
@Service
@Transactional
public class ReceiptServiceImpl extends BaseEntityServiceImpl<ReceiptDTO, ReceiptDBEntity> implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    private final ReceiptMapper receiptMapper;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, ReceiptMapper receiptMapper) {
    	super(receiptRepository, receiptMapper, LoggerFactory.getLogger(ReceiptServiceImpl.class));
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
    }
}
