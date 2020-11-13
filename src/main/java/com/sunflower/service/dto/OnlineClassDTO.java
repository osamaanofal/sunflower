package com.sunflower.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.sunflower.domain.enumeration.Status;

import lombok.Data;

/**
 * A DTO for the {@link com.sunflower.domain.OnlineClassDBEntity} entity.
 */
@Data
public class OnlineClassDTO extends BaseEntityDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1208138583424098668L;

    @NotNull
    @Size(min = 2, max = 200)
    private String title;

    @NotNull
    private Status status;

    private Long courseId;

    private Long teacherId;

    private String teacherName;
    
    private String courseTitle;
    
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
