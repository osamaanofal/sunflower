package com.sunflower.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sunflower.domain.ReceiptDBEntity;
import com.sunflower.service.ReceiptService;
import com.sunflower.service.dto.ReceiptDTO;
import com.sunflower.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sunflower.domain.ReceiptDBEntity}.
 */
@RestController
@RequestMapping("/api/receipts")
public class ReceiptResource extends BaseEntityResource<ReceiptDTO, ReceiptDBEntity> {

	private final ReceiptService receiptService;

	public ReceiptResource(ReceiptService receiptService) {
		super(receiptService, LoggerFactory.getLogger(ReceiptResource.class));
		this.receiptService = receiptService;
	}
}
