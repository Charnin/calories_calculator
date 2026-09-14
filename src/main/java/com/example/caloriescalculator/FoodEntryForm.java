package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class FoodEntryForm {
    @NotNull(message = "{validation.date.required}")
    @PastOrPresent(message = "{validation.date.future}")
    private LocalDate logDate = LocalDate.now();

    @NotBlank(message = "{validation.meal.required}")
    private String mealType = "breakfast";

    @NotBlank(message = "{validation.food.required}")
    @Size(max = 160, message = "{validation.food.size}")
    private String foodName;

    @NotNull(message = "{validation.quantity.required}")
    @DecimalMin(value = "0.01", message = "{validation.quantity.positive}")
    private Double servingQuantity = 1.0;

    @NotBlank(message = "{validation.unit.required}")
    @Pattern(regexp = "^(g|ml)$", message = "{validation.unit.invalid}")
    private String servingUnit = "g";
    private String servingDescription;
    private String dataSource = "manual";
    private String externalFoodId;

    @NotNull(message = "{validation.calories.required}")
    @DecimalMin(value = "0.0", message = "{validation.calories.nonnegative}")
    private Double calories;

    @NotNull(message = "{validation.protein.required}")
    @DecimalMin(value = "0.0", message = "{validation.protein.nonnegative}")
    private Double proteinGrams = 0.0;

    @NotNull(message = "{validation.carbs.required}")
    @DecimalMin(value = "0.0", message = "{validation.carbs.nonnegative}")
    private Double carbohydrateGrams = 0.0;

    @NotNull(message = "{validation.fat.required}")
    @DecimalMin(value = "0.0", message = "{validation.fat.nonnegative}")
    private Double fatGrams = 0.0;

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public Double getServingQuantity() { return servingQuantity; }
    public void setServingQuantity(Double servingQuantity) { this.servingQuantity = servingQuantity; }
    public String getServingUnit() { return servingUnit; }
    public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }
    public String getServingDescription() { return servingDescription; }
    public void setServingDescription(String servingDescription) { this.servingDescription = servingDescription; }
    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }
    public String getExternalFoodId() { return externalFoodId; }
    public void setExternalFoodId(String externalFoodId) { this.externalFoodId = externalFoodId; }
    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }
    public Double getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(Double proteinGrams) { this.proteinGrams = proteinGrams; }
    public Double getCarbohydrateGrams() { return carbohydrateGrams; }
    public void setCarbohydrateGrams(Double carbohydrateGrams) { this.carbohydrateGrams = carbohydrateGrams; }
    public Double getFatGrams() { return fatGrams; }
    public void setFatGrams(Double fatGrams) { this.fatGrams = fatGrams; }

    public static FoodEntryForm from(FoodEntry entry) {
        FoodEntryForm form = new FoodEntryForm();
        form.setLogDate(entry.getLogDate());
        form.setMealType(entry.getMealType());
        form.setFoodName(entry.getFoodName());
        form.setServingQuantity(entry.getServingQuantity());
        form.setServingUnit("ml".equals(entry.getServingUnit()) ? "ml" : "g");
        form.setServingDescription(entry.getServingDescription());
        form.setDataSource(entry.getDataSource());
        form.setExternalFoodId(entry.getExternalFoodId());
        form.setCalories(entry.getCalories());
        form.setProteinGrams(entry.getProteinGrams());
        form.setCarbohydrateGrams(entry.getCarbohydrateGrams());
        form.setFatGrams(entry.getFatGrams());
        return form;
    }
}
