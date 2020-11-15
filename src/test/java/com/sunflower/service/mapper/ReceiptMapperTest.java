package com.sunflower.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sunflower.mapper.ReceiptMapper;
import com.sunflower.mapper.ReceiptMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class ReceiptMapperTest {

    private ReceiptMapper receiptMapper;

    @BeforeEach
    public void setUp() {
        receiptMapper = new ReceiptMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(receiptMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(receiptMapper.fromId(null)).isNull();
    }
}
