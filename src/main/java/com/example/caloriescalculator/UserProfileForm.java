package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserProfileForm {
    private String bodyFatMode = "skip";

    @NotNull(message = "กรุณากรอกอายุ")
    @Min(value = 15, message = "ระบบนี้รองรับอายุตั้งแต่ 15 ปี")
    @Max(value = 100, message = "กรุณาตรวจสอบอายุอีกครั้ง")
    private Integer age;

    @NotBlank(message = "กรุณาเลือกเพศที่ใช้คำนวณ")
    private String sex;

    @NotNull(message = "กรุณากรอกน้ำหนัก")
    @DecimalMin(value = "30.0", message = "น้ำหนักต้องไม่น้อยกว่า 30 กก.")
    @DecimalMax(value = "350.0", message = "กรุณาตรวจสอบน้ำหนักอีกครั้ง")
    private Double weightKg;

    @NotNull(message = "กรุณากรอกส่วนสูง")
    @DecimalMin(value = "120.0", message = "ส่วนสูงต้องไม่น้อยกว่า 120 ซม.")
    @DecimalMax(value = "250.0", message = "กรุณาตรวจสอบส่วนสูงอีกครั้ง")
    private Double heightCm;

    private Double bodyFatPercent;

    private Double neckCm;

    private Double waistCm;

    private Double hipCm;

    @NotNull(message = "กรุณาเลือกระดับกิจกรรม")
    private Double activityLevel = 1.2;

    @NotBlank(message = "กรุณาเลือกเป้าหมาย")
    private String goal = "maintain";

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }
    public Double getBodyFatPercent() { return bodyFatPercent; }
    public void setBodyFatPercent(Double bodyFatPercent) { this.bodyFatPercent = bodyFatPercent; }
    public String getBodyFatMode() { return bodyFatMode; }
    public void setBodyFatMode(String bodyFatMode) { this.bodyFatMode = bodyFatMode; }
    public Double getNeckCm() { return neckCm; }
    public void setNeckCm(Double neckCm) { this.neckCm = neckCm; }
    public Double getWaistCm() { return waistCm; }
    public void setWaistCm(Double waistCm) { this.waistCm = waistCm; }
    public Double getHipCm() { return hipCm; }
    public void setHipCm(Double hipCm) { this.hipCm = hipCm; }
    public Double getActivityLevel() { return activityLevel; }
    public void setActivityLevel(Double activityLevel) { this.activityLevel = activityLevel; }
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
