package com.sunflower.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TeacherDBEntity.
 */
@Entity
@Table(name = "teacher")
public class TeacherDBEntity implements Serializable {

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

    @OneToMany(mappedBy = "teacher")
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

    public TeacherDBEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TeacherDBEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<OnlineClassDBEntity> getOnlineClasses() {
        return onlineClasses;
    }

    public TeacherDBEntity onlineClasses(Set<OnlineClassDBEntity> onlineClasses) {
        this.onlineClasses = onlineClasses;
        return this;
    }

    public TeacherDBEntity addOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.add(onlineClass);
        onlineClass.setTeacher(this);
        return this;
    }

    public TeacherDBEntity removeOnlineClass(OnlineClassDBEntity onlineClass) {
        this.onlineClasses.remove(onlineClass);
        onlineClass.setTeacher(null);
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
        if (!(o instanceof TeacherDBEntity)) {
            return false;
        }
        return id != null && id.equals(((TeacherDBEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeacherDBEntity{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
