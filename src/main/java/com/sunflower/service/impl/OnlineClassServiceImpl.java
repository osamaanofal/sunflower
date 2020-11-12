package com.sunflower.service.impl;

import com.sunflower.service.OnlineClassService;
import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.repository.OnlineClassRepository;
import com.sunflower.service.dto.OnlineClassDTO;
import com.sunflower.service.mapper.OnlineClassMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OnlineClassDBEntity}.
 */
@Service
@Transactional
public class OnlineClassServiceImpl implements OnlineClassService {

    private final Logger log = LoggerFactory.getLogger(OnlineClassServiceImpl.class);

    private final OnlineClassRepository onlineClassRepository;

    private final OnlineClassMapper onlineClassMapper;

    public OnlineClassServiceImpl(OnlineClassRepository onlineClassRepository, OnlineClassMapper onlineClassMapper) {
        this.onlineClassRepository = onlineClassRepository;
        this.onlineClassMapper = onlineClassMapper;
    }

    @Override
    public OnlineClassDTO save(OnlineClassDTO onlineClassDTO) {
        log.debug("Request to save OnlineClass : {}", onlineClassDTO);
        OnlineClassDBEntity onlineClassDBEntity = onlineClassMapper.toEntity(onlineClassDTO);
        onlineClassDBEntity = onlineClassRepository.save(onlineClassDBEntity);
        return onlineClassMapper.toDto(onlineClassDBEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OnlineClassDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OnlineClasses");
        return onlineClassRepository.findAll(pageable)
            .map(onlineClassMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OnlineClassDTO> findOne(Long id) {
        log.debug("Request to get OnlineClass : {}", id);
        return onlineClassRepository.findById(id)
            .map(onlineClassMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OnlineClass : {}", id);
        onlineClassRepository.deleteById(id);
    }
}
