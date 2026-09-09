package com.example.caloriescalculator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
    @NotBlank(message = "กรุณากรอกชื่อที่ต้องการให้แสดง")
    @Size(max = 80, message = "ชื่อต้องไม่เกิน 80 ตัวอักษร")
    private String displayName;

    @NotBlank(message = "กรุณากรอกอีเมล")
    @Email(message = "รูปแบบอีเมลไม่ถูกต้อง")
    private String email;

    @NotBlank(message = "กรุณากรอกรหัสผ่าน")
    @Size(min = 8, max = 72, message = "รหัสผ่านต้องมี 8–72 ตัวอักษร")
    @Pattern(regexp = "^[\\x21-\\x7E]+$", message = "รหัสผ่านใช้ได้เฉพาะอักษรอังกฤษ ตัวเลข และสัญลักษณ์")
    private String password;

    @NotBlank(message = "กรุณายืนยันรหัสผ่าน")
    private String confirmPassword;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
