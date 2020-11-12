package com.sunflower.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sunflower.domain.enumeration.Status;

/**
 * A OnlineClassDBEntity.
 */
@Entity
@Table(name = "online_class")
public class OnlineClassDBEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties(value = "onlineClasses", allowSetters = true)
    private CourseDBEntity course;

    @ManyToOne
    @JsonIgnoreProperties(value = "onlineClasses", allowSetters = true)
    private TeacherDBEntity teacher;

    @ManyToMany(mappedBy = "onlineClasses")
    @JsonIgnore
    private Set<StudentDBEntity> students = new HashSet<>();

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

    public OnlineClassDBEntity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public OnlineClassDBEntity status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CourseDBEntity getCourse() {
        return course;
    }

    public OnlineClassDBEntity course(CourseDBEntity course) {
        this.course = course;
        return this;
    }

    public void setCourse(CourseDBEntity course) {
        this.course = course;
    }

    public TeacherDBEntity getTeacher() {
        return teacher;
    }

    public OnlineClassDBEntity teacher(TeacherDBEntity teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(TeacherDBEntity teacher) {
        this.teacher = teacher;
    }

    public Set<StudentDBEntity> getStudents() {
        return students;
    }

    public OnlineClassDBEntity students(Set<StudentDBEntity> students) {
        this.students = students;
        return this;
    }

    public OnlineClassDBEntity addStudent(StudentDBEntity student) {
        this.students.add(student);
        student.getOnlineClasses().add(this);
        return this;
    }

    public OnlineClassDBEntity removeStudent(StudentDBEntity student) {
        this.students.remove(student);
        student.getOnlineClasses().remove(this);
        return this;
    }

    public void setStudents(Set<StudentDBEntity> students) {
        this.students = students;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OnlineClassDBEntity)) {
            return false;
        }
        return id != null && id.equals(((OnlineClassDBEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OnlineClassDBEntity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
