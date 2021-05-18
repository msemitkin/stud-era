package ua.knu.csc.studera.web.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCourseDTO {
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name must be not blank")
    private String name;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description must be not blank")
    private String description;
    @NotNull(message = "Students limit is required")
    @Min(1)
    private Integer studentsLimit;

    public CreateCourseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStudentsLimit() {
        return studentsLimit;
    }

    public void setStudentsLimit(Integer studentsLimit) {
        this.studentsLimit = studentsLimit;
    }

}
