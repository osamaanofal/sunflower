package com.sunflower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class CourseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CourseDBEntity.class);
        CourseDBEntity courseDBEntity1 = new CourseDBEntity();
        courseDBEntity1.setId(1L);
        CourseDBEntity courseDBEntity2 = new CourseDBEntity();
        courseDBEntity2.setId(courseDBEntity1.getId());
        assertThat(courseDBEntity1).isEqualTo(courseDBEntity2);
        courseDBEntity2.setId(2L);
        assertThat(courseDBEntity1).isNotEqualTo(courseDBEntity2);
        courseDBEntity1.setId(null);
        assertThat(courseDBEntity1).isNotEqualTo(courseDBEntity2);
    }
}
