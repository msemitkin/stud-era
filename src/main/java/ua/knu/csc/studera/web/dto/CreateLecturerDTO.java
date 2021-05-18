package ua.knu.csc.studera.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateLecturerDTO {
    @NotNull(message = "First name is required")
    @NotBlank(message = "First name must be not blank")
    private String firstName;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name must be not blank")
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
}
