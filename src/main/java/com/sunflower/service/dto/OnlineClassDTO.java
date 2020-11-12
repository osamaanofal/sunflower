package com.sunflower.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.sunflower.domain.enumeration.Status;

/**
 * A DTO for the {@link com.sunflower.domain.OnlineClassDBEntity} entity.
 */
public class OnlineClassDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    private String title;

    @NotNull
    private Status status;


    private Long courseId;

    private Long teacherId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OnlineClassDTO)) {
            return false;
        }

        return id != null && id.equals(((OnlineClassDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OnlineClassDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", courseId=" + getCourseId() +
            ", teacherId=" + getTeacherId() +
            "}";
    }
}
