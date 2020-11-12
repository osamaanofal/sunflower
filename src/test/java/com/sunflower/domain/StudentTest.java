package com.sunflower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class StudentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentDBEntity.class);
        StudentDBEntity studentDBEntity1 = new StudentDBEntity();
        studentDBEntity1.setId(1L);
        StudentDBEntity studentDBEntity2 = new StudentDBEntity();
        studentDBEntity2.setId(studentDBEntity1.getId());
        assertThat(studentDBEntity1).isEqualTo(studentDBEntity2);
        studentDBEntity2.setId(2L);
        assertThat(studentDBEntity1).isNotEqualTo(studentDBEntity2);
        studentDBEntity1.setId(null);
        assertThat(studentDBEntity1).isNotEqualTo(studentDBEntity2);
    }
}
