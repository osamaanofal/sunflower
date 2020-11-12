package com.sunflower.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.sunflower.domain.ReceiptDBEntity} entity.
 */
public class ReceiptDTO extends BaseEntityDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8888938738331606298L;

    @NotNull
    private String receiptNo;

    @NotNull
    private LocalDate receiptDate;

    @NotNull
    private BigDecimal amount;

    @Size(max = 500)
    private String description;


    private Long studentId;

    private Long courseId;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptDTO)) {
            return false;
        }

        return id != null && id.equals(((ReceiptDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "id=" + getId() +
            ", receiptNo='" + getReceiptNo() + "'" +
            ", receiptDate='" + getReceiptDate() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", studentId=" + getStudentId() +
            ", courseId=" + getCourseId() +
            "}";
    }
}
