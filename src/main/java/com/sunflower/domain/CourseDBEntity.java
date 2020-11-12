package com.sunflower.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A CourseDBEntity.
 */
@Entity
@Table(name = "course")
public class CourseDBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "title", length = 200, nullable = false, unique = true)
    private String title;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @NotNull
    @Column(name = "price", precision = 21, scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "course")
    private Set<OnlineClassDBEntity> onlineClasses = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<ReceiptDBEntity> receipts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CourseDBEntity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public CourseDBEntity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public CourseDBEntity price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<OnlineClassDBEntity> getOnlineClasses() {
        return onlineClasses;
    }

    public CourseDBEntity onlineClasses(Set<OnlineClassDBEntity> onlineClasses) {
        this.onlineClasses = onlineClasses;
        return this;
    }

    public CourseDBEntity addOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.add(onlineClass);
        onlineClass.setCourse(this);
        return this;
    }

    public CourseDBEntity removeOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.remove(onlineClass);
        onlineClass.setCourse(null);
        return this;
    }

    public void setOnlineClasses(Set<OnlineClassDBEntity> onlineClasses) {
        this.onlineClasses = onlineClasses;
    }

    public Set<ReceiptDBEntity> getReceipts() {
        return receipts;
    }

    public CourseDBEntity receipts(Set<ReceiptDBEntity> receipts) {
        this.receipts = receipts;
        return this;
    }

    public CourseDBEntity addReceipt(ReceiptDBEntity receipt) {
        this.receipts.add(receipt);
        receipt.setCourse(this);
        return this;
    }

    public CourseDBEntity removeReceipt(ReceiptDBEntity receipt) {
        this.receipts.remove(receipt);
        receipt.setCourse(null);
        return this;
    }

    public void setReceipts(Set<ReceiptDBEntity> receipts) {
        this.receipts = receipts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDBEntity)) {
            return false;
        }
        return id != null && id.equals(((CourseDBEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDBEntity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
