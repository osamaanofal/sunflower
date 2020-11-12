package com.sunflower.web.rest;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.service.OnlineClassService;
import com.sunflower.service.dto.OnlineClassDTO;

/**
 * REST controller for managing
 * {@link com.sunflower.domain.OnlineClassDBEntity}.
 */
@RestController
@RequestMapping("/api/online-classes")
public class OnlineClassResource extends BaseEntityResource<OnlineClassDTO, OnlineClassDBEntity> {

	private final OnlineClassService onlineClassService;

	public OnlineClassResource(OnlineClassService onlineClassService) {

		super(onlineClassService, LoggerFactory.getLogger(OnlineClassResource.class));
		this.onlineClassService = onlineClassService;
	}

}
