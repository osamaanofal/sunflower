package com.sunflower.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sunflower.domain.TeacherDBEntity} entity.
 */
public class TeacherDTO extends BaseEntityDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -134172128369623217L;

	@NotNull
	@Size(min = 2, max = 200)
	private String firstName;

	@NotNull
	@Size(min = 2, max = 200)
	private String lastName;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TeacherDTO)) {
			return false;
		}

		return id != null && id.equals(((TeacherDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "TeacherDTO{" + "id=" + getId() + ", firstName='" + getFirstName() + "'" + ", lastName='" + getLastName()
				+ "'" + "}";
	}
}
