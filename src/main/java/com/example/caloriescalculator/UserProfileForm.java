package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserProfileForm {
    private String bodyFatMode = "skip";

    @NotNull(message = "{validation.age.required}")
    @Min(value = 15, message = "{validation.age.min}")
    @Max(value = 100, message = "{validation.age.max}")
    private Integer age;

    @NotBlank(message = "{validation.sex.required}")
    private String sex;

    @NotNull(message = "{validation.weight.required}")
    @DecimalMin(value = "30.0", message = "{validation.weight.min}")
    @DecimalMax(value = "350.0", message = "{validation.weight.max}")
    private Double weightKg;

    @NotNull(message = "{validation.height.required}")
    @DecimalMin(value = "120.0", message = "{validation.height.min}")
    @DecimalMax(value = "250.0", message = "{validation.height.max}")
    private Double heightCm;

    private Double bodyFatPercent;

    private Double neckCm;

    private Double waistCm;

    private Double hipCm;

    @NotNull(message = "{validation.activity.required}")
    private Double activityLevel = 1.2;

    @NotBlank(message = "{validation.goal.required}")
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
