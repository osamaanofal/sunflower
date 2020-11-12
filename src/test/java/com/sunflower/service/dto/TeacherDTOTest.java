package com.sunflower.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class TeacherDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherDTO.class);
        TeacherDTO teacherDTO1 = new TeacherDTO();
        teacherDTO1.setId(1L);
        TeacherDTO teacherDTO2 = new TeacherDTO();
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2);
        teacherDTO2.setId(teacherDTO1.getId());
        assertThat(teacherDTO1).isEqualTo(teacherDTO2);
        teacherDTO2.setId(2L);
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2);
        teacherDTO1.setId(null);
        assertThat(teacherDTO1).isNotEqualTo(teacherDTO2);
    }
}
