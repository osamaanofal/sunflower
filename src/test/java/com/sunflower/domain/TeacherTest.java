package com.sunflower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class TeacherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeacherDBEntity.class);
        TeacherDBEntity teacherDBEntity1 = new TeacherDBEntity();
        teacherDBEntity1.setId(1L);
        TeacherDBEntity teacherDBEntity2 = new TeacherDBEntity();
        teacherDBEntity2.setId(teacherDBEntity1.getId());
        assertThat(teacherDBEntity1).isEqualTo(teacherDBEntity2);
        teacherDBEntity2.setId(2L);
        assertThat(teacherDBEntity1).isNotEqualTo(teacherDBEntity2);
        teacherDBEntity1.setId(null);
        assertThat(teacherDBEntity1).isNotEqualTo(teacherDBEntity2);
    }
}
