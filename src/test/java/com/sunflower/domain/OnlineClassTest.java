package com.sunflower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class OnlineClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OnlineClassDBEntity.class);
        OnlineClassDBEntity onlineClassDBEntity1 = new OnlineClassDBEntity();
        onlineClassDBEntity1.setId(1L);
        OnlineClassDBEntity onlineClassDBEntity2 = new OnlineClassDBEntity();
        onlineClassDBEntity2.setId(onlineClassDBEntity1.getId());
        assertThat(onlineClassDBEntity1).isEqualTo(onlineClassDBEntity2);
        onlineClassDBEntity2.setId(2L);
        assertThat(onlineClassDBEntity1).isNotEqualTo(onlineClassDBEntity2);
        onlineClassDBEntity1.setId(null);
        assertThat(onlineClassDBEntity1).isNotEqualTo(onlineClassDBEntity2);
    }
}
