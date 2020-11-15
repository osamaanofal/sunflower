package com.sunflower.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sunflower.mapper.OnlineClassMapper;
import com.sunflower.mapper.OnlineClassMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class OnlineClassMapperTest {

    private OnlineClassMapper onlineClassMapper;

    @BeforeEach
    public void setUp() {
        onlineClassMapper = new OnlineClassMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(onlineClassMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(onlineClassMapper.fromId(null)).isNull();
    }
}
