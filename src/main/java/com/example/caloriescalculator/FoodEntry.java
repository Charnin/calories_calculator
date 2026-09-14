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
@Table(name = "food_entries")
public class FoodEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private LocalDate logDate;
    private String mealType;
    private String foodName;
    private Double servingQuantity;
    private String servingUnit;
    private String servingDescription;
    private String dataSource;
    private String externalFoodId;
    private Double calories;
    private Double proteinGrams;
    private Double carbohydrateGrams;
    private Double fatGrams;
    private Instant createdAt;

    protected FoodEntry() { }

    public FoodEntry(AppUser user, FoodEntryForm form) {
        this.user = user;
        update(form);
        this.createdAt = Instant.now();
    }

    public void update(FoodEntryForm form) {
        this.logDate = form.getLogDate();
        this.mealType = form.getMealType();
        this.foodName = form.getFoodName().trim();
        this.servingQuantity = form.getServingQuantity();
        this.servingUnit = form.getServingUnit();
        this.servingDescription = clean(form.getServingDescription(), 120);
        this.dataSource = switch (form.getDataSource() == null ? "manual" : form.getDataSource()) {
            case "reference", "estimate", "custom" -> form.getDataSource();
            default -> "manual";
        };
        this.externalFoodId = clean(form.getExternalFoodId(), 80);
        this.calories = form.getCalories();
        this.proteinGrams = form.getProteinGrams();
        this.carbohydrateGrams = form.getCarbohydrateGrams();
        this.fatGrams = form.getFatGrams();
    }

    public FoodEntry copyFor(AppUser owner, LocalDate date) {
        FoodEntry copy = new FoodEntry();
        copy.user = owner;
        copy.logDate = date;
        copy.mealType = mealType;
        copy.foodName = foodName;
        copy.servingQuantity = servingQuantity;
        copy.servingUnit = servingUnit;
        copy.servingDescription = servingDescription;
        copy.dataSource = dataSource;
        copy.externalFoodId = externalFoodId;
        copy.calories = calories;
        copy.proteinGrams = proteinGrams;
        copy.carbohydrateGrams = carbohydrateGrams;
        copy.fatGrams = fatGrams;
        copy.createdAt = Instant.now();
        return copy;
    }

    public void scaleBy(double multiplier) {
        if (multiplier <= 0) throw new IllegalArgumentException("Multiplier must be positive");
        this.servingQuantity = round(this.getServingQuantity() * multiplier);
        this.calories = round(this.calories * multiplier);
        this.proteinGrams = round(this.proteinGrams * multiplier);
        this.carbohydrateGrams = round(this.carbohydrateGrams * multiplier);
        this.fatGrams = round(this.fatGrams * multiplier);
    }

    private double round(double value) { return Math.round(value * 100.0) / 100.0; }
    private String clean(String value, int limit) {
        if (value == null || value.isBlank()) return null;
        String cleaned = value.trim();
        return cleaned.substring(0, Math.min(cleaned.length(), limit));
    }

    public Long getId() { return id; }
    public LocalDate getLogDate() { return logDate; }
    public String getMealType() { return mealType; }
    public String getFoodName() { return foodName; }
    public Double getServingQuantity() { return servingQuantity == null ? 1.0 : servingQuantity; }
    public String getServingUnit() { return servingUnit; }
    public String getServingDescription() { return servingDescription; }
    public String getServingUnitLabel() {
        if (servingUnit == null) {
            return servingDescription == null || servingDescription.isBlank() ? "หน่วยบริโภค" : servingDescription;
        }
        return switch (servingUnit) {

            case "ml" -> "มิลลิลิตร";
            default -> "กรัม";
        };
    }
    public String getDataSource() { return dataSource; }
    public String getExternalFoodId() { return externalFoodId; }
    public Double getCalories() { return calories; }
    public Double getProteinGrams() { return proteinGrams; }
    public Double getCarbohydrateGrams() { return carbohydrateGrams; }
    public Double getFatGrams() { return fatGrams; }
    public Instant getCreatedAt() { return createdAt; }
}
