package com.sunflower.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A StudentDBEntity.
 */
@Entity
@Table(name = "student")
public class StudentDBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "last_name", length = 200, nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "student")
    private Set<ReceiptDBEntity> receipts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "student_online_class",
               joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "online_class_id", referencedColumnName = "id"))
    private Set<OnlineClassDBEntity> onlineClasses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public StudentDBEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public StudentDBEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ReceiptDBEntity> getReceipts() {
        return receipts;
    }

    public StudentDBEntity receipts(Set<ReceiptDBEntity> receipts) {
        this.receipts = receipts;
        return this;
    }

    public StudentDBEntity addReceipt(ReceiptDBEntity receipt) {
        this.receipts.add(receipt);
        receipt.setStudent(this);
        return this;
    }

    public StudentDBEntity removeReceipt(ReceiptDBEntity receipt) {
        this.receipts.remove(receipt);
        receipt.setStudent(null);
        return this;
    }

    public void setReceipts(Set<ReceiptDBEntity> receipts) {
        this.receipts = receipts;
    }

    public Set<OnlineClassDBEntity> getOnlineClasses() {
        return onlineClasses;
    }

    public StudentDBEntity onlineClasses(Set<OnlineClassDBEntity> onlineClasses) {
        this.onlineClasses = onlineClasses;
        return this;
    }

    public StudentDBEntity addOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.add(onlineClass);
        onlineClass.getStudents().add(this);
        return this;
    }

    public StudentDBEntity removeOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.remove(onlineClass);
        onlineClass.getStudents().remove(this);
        return this;
    }

    public void setOnlineClasses(Set<OnlineClassDBEntity> onlineClasses) {
        this.onlineClasses = onlineClasses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDBEntity)) {
            return false;
        }
        return id != null && id.equals(((StudentDBEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDBEntity{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
