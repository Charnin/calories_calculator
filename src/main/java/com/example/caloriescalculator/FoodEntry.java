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
    private Double portionCount;
    private Double baseServingQuantity;
    private String baseServingLabel;
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
        this.portionCount = form.getPortionCount();
        this.baseServingQuantity = form.getBaseServingQuantity();
        this.baseServingLabel = clean(form.getBaseServingLabel(), 80);
        this.calories = form.getCalories();
        this.proteinGrams = form.getProteinGrams();
        this.carbohydrateGrams = form.getCarbohydrateGrams();
        this.fatGrams = form.getFatGrams();
    }

    public void adjustPortions(double delta) {
        double currentCount = portionCount == null || portionCount <= 0 ? 1.0 : portionCount;
        double newCount = delta < 0 && currentCount <= 1.0 ? currentCount : currentCount + delta;
        double factor = newCount / currentCount;
        if (baseServingQuantity == null || baseServingQuantity <= 0) {
            baseServingQuantity = getServingQuantity() / currentCount;
        }
        this.servingQuantity = round(this.getServingQuantity() * factor);
        this.calories = round(this.calories * factor);
        this.proteinGrams = round(this.proteinGrams * factor);
        this.carbohydrateGrams = round(this.carbohydrateGrams * factor);
        this.fatGrams = round(this.fatGrams * factor);
        this.portionCount = newCount;
        if (baseServingLabel != null) this.servingDescription = formatCount(newCount) + " × " + baseServingLabel;
    }

    private double round(double value) { return Math.round(value * 100.0) / 100.0; }
    private String clean(String value, int limit) {
        if (value == null || value.isBlank()) return null;
        String cleaned = value.trim();
        return cleaned.substring(0, Math.min(cleaned.length(), limit));
    }
    private String formatCount(double value) { return value == Math.rint(value) ? String.valueOf((long) value) : String.valueOf(value); }

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
    public Double getPortionCount() { return portionCount == null ? 1.0 : portionCount; }
    public String getPortionCountLabel() { return formatCount(getPortionCount()); }
    public Double getBaseServingQuantity() { return baseServingQuantity; }
    public String getBaseServingLabel() { return baseServingLabel; }
    public Double getCalories() { return calories; }
    public Double getProteinGrams() { return proteinGrams; }
    public Double getCarbohydrateGrams() { return carbohydrateGrams; }
    public Double getFatGrams() { return fatGrams; }
    public Instant getCreatedAt() { return createdAt; }
}
