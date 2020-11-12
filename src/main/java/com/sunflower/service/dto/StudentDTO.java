package com.sunflower.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.sunflower.domain.StudentDBEntity} entity.
 */
public class StudentDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 200)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 200)
    private String lastName;

    private Set<OnlineClassDTO> onlineClasses = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<OnlineClassDTO> getOnlineClasses() {
        return onlineClasses;
    }

    public void setOnlineClasses(Set<OnlineClassDTO> onlineClasses) {
        this.onlineClasses = onlineClasses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentDTO)) {
            return false;
        }

        return id != null && id.equals(((StudentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", onlineClasses='" + getOnlineClasses() + "'" +
            "}";
    }
}
