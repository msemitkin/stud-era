package ua.knu.csc.studera.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateProviderDTO {
    @NotNull(message = "Provider name is required")
    @NotBlank(message = "Provider name must be not blank")
    private String name;
    @NotNull(message = "Provider description is required")
    @NotBlank(message = "Provider description must be not blank")
    private String description;

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
}
