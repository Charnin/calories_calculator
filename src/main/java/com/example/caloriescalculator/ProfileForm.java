package com.example.caloriescalculator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileForm {
    @NotBlank(message = "กรุณากรอกชื่อที่แสดง")
    @Size(max = 80, message = "ชื่อต้องไม่เกิน 80 ตัวอักษร")
    private String displayName;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
