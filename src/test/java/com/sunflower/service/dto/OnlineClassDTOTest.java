package com.sunflower.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class OnlineClassDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OnlineClassDTO.class);
        OnlineClassDTO onlineClassDTO1 = new OnlineClassDTO();
        onlineClassDTO1.setId(1L);
        OnlineClassDTO onlineClassDTO2 = new OnlineClassDTO();
        assertThat(onlineClassDTO1).isNotEqualTo(onlineClassDTO2);
        onlineClassDTO2.setId(onlineClassDTO1.getId());
        assertThat(onlineClassDTO1).isEqualTo(onlineClassDTO2);
        onlineClassDTO2.setId(2L);
        assertThat(onlineClassDTO1).isNotEqualTo(onlineClassDTO2);
        onlineClassDTO1.setId(null);
        assertThat(onlineClassDTO1).isNotEqualTo(onlineClassDTO2);
    }
}
