package com.sunflower.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sunflower.mapper.TeacherMapper;
import com.sunflower.mapper.TeacherMapperImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    @BeforeEach
    public void setUp() {
        teacherMapper = new TeacherMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(teacherMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(teacherMapper.fromId(null)).isNull();
    }
}
