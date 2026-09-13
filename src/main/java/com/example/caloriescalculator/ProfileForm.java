package com.example.caloriescalculator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileForm {
    @NotBlank(message = "{validation.name.required}")
    @Size(max = 80, message = "{validation.name.size}")
    private String displayName;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
