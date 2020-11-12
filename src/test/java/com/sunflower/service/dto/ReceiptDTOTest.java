package com.sunflower.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sunflower.web.rest.TestUtil;

public class ReceiptDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReceiptDTO.class);
        ReceiptDTO receiptDTO1 = new ReceiptDTO();
        receiptDTO1.setId(1L);
        ReceiptDTO receiptDTO2 = new ReceiptDTO();
        assertThat(receiptDTO1).isNotEqualTo(receiptDTO2);
        receiptDTO2.setId(receiptDTO1.getId());
        assertThat(receiptDTO1).isEqualTo(receiptDTO2);
        receiptDTO2.setId(2L);
        assertThat(receiptDTO1).isNotEqualTo(receiptDTO2);
        receiptDTO1.setId(null);
        assertThat(receiptDTO1).isNotEqualTo(receiptDTO2);
    }
}
