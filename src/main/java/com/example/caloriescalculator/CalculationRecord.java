package com.example.caloriescalculator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "calculation_records")
public class CalculationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private LocalDate planDate;
    private Instant calculatedAt;
    private Integer age;
    private String sex;
    private Double weightKg;
    private Double heightCm;
    private Double bodyFatPercent;
    private Double activityLevel;
    private String goal;
    private Long bmr;
    private Long maintenanceCalories;
    private Long calorieTarget;
    private Long proteinGrams;
    private Long carbohydrateGrams;
    private Long fatGrams;
    private String calculationMethod;

    protected CalculationRecord() { }

    public CalculationRecord(AppUser user, UserProfileForm profile, CalculationResult result) {
        this.user = user;
        this.planDate = LocalDate.now();
        this.calculatedAt = Instant.now();
        this.age = profile.getAge();
        this.sex = profile.getSex();
        this.weightKg = profile.getWeightKg();
        this.heightCm = profile.getHeightCm();
        this.bodyFatPercent = profile.getBodyFatPercent();
        this.activityLevel = profile.getActivityLevel();
        this.goal = profile.getGoal();
        this.bmr = result.bmr();
        this.maintenanceCalories = result.maintenanceCalories();
        this.calorieTarget = result.calorieTarget();
        this.proteinGrams = result.proteinGrams();
        this.carbohydrateGrams = result.carbohydrateGrams();
        this.fatGrams = result.fatGrams();
        this.calculationMethod = result.calculationMethod();
    }

    public Long getId() { return id; }
    public LocalDate getPlanDate() { return planDate; }
    public Instant getCalculatedAt() { return calculatedAt; }
    public Integer getAge() { return age; }
    public String getSex() { return sex; }
    public Double getWeightKg() { return weightKg; }
    public Double getHeightCm() { return heightCm; }
    public Double getBodyFatPercent() { return bodyFatPercent; }
    public Double getActivityLevel() { return activityLevel; }
    public String getGoal() { return goal; }
    public Long getBmr() { return bmr; }
    public Long getMaintenanceCalories() { return maintenanceCalories; }
    public Long getCalorieTarget() { return calorieTarget; }
    public Long getProteinGrams() { return proteinGrams; }
    public Long getCarbohydrateGrams() { return carbohydrateGrams; }
    public Long getFatGrams() { return fatGrams; }
    public String getCalculationMethod() { return calculationMethod; }
}
