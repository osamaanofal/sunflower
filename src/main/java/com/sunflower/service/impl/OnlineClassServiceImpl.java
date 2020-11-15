package com.sunflower.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.domain.enumeration.Status;
import com.sunflower.mapper.OnlineClassMapper;
import com.sunflower.repository.OnlineClassRepository;
import com.sunflower.service.OnlineClassService;
import com.sunflower.service.dto.OnlineClassDTO;

/**
 * Service Implementation for managing {@link OnlineClassDBEntity}.
 */
@Service
@Transactional
public class OnlineClassServiceImpl extends BaseEntityServiceImpl<OnlineClassDTO, OnlineClassDBEntity> implements OnlineClassService {

    private final OnlineClassRepository onlineClassRepository;

    private final OnlineClassMapper onlineClassMapper;

    public OnlineClassServiceImpl(OnlineClassRepository onlineClassRepository, OnlineClassMapper onlineClassMapper) {
    	super(onlineClassRepository, onlineClassMapper, LoggerFactory.getLogger(OnlineClassServiceImpl.class));
        this.onlineClassRepository = onlineClassRepository;
        this.onlineClassMapper = onlineClassMapper;
    }
	@Override
	public Boolean closeOnlineClass(Long classId) throws Exception {

		OnlineClassDBEntity onlineClass = onlineClassRepository.findById(classId)
				.orElseThrow(()->  new Exception("Class Not Found"));

		onlineClass.setStatus(Status.CLOSED);
		
		onlineClassRepository.save(onlineClass);
				
		return true;
	}

	@Override
	public Boolean availOnlineClass(Long classId) throws Exception {
		OnlineClassDBEntity onlineClass = onlineClassRepository.findById(classId)
				.orElseThrow(()->  new Exception("Class Not Found"));

		onlineClass.setStatus(Status.CLOSED);
		
		onlineClassRepository.save(onlineClass);
		
		return true;
	}

	@Override
    @Transactional(readOnly = true)
	public List<OnlineClassDBEntity> getOpenClassesMappedToCourses() {
		return onlineClassRepository.getOpenClassesMappedToCourses();
	}
}
