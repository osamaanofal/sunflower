package com.sunflower.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sunflower.mapper.StudentMapper;
import com.sunflower.mapper.StudentMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentMapperTest {

    private StudentMapper studentMapper;

    @BeforeEach
    public void setUp() {
        studentMapper = new StudentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(studentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(studentMapper.fromId(null)).isNull();
    }
}
