package com.sunflower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class ReceiptTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptDBEntity.class);
        ReceiptDBEntity receiptDBEntity1 = new ReceiptDBEntity();
        receiptDBEntity1.setId(1L);
        ReceiptDBEntity receiptDBEntity2 = new ReceiptDBEntity();
        receiptDBEntity2.setId(receiptDBEntity1.getId());
        assertThat(receiptDBEntity1).isEqualTo(receiptDBEntity2);
        receiptDBEntity2.setId(2L);
        assertThat(receiptDBEntity1).isNotEqualTo(receiptDBEntity2);
        receiptDBEntity1.setId(null);
        assertThat(receiptDBEntity1).isNotEqualTo(receiptDBEntity2);
    }
}
