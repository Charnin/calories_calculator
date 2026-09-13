package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomFoodForm {
    @NotBlank(message = "{validation.food.required}")
    @Size(max = 160, message = "{validation.food.size}")
    private String name;

    @Size(max = 120, message = "{validation.brand.size}")
    private String brand = "";

    @NotBlank(message = "{validation.referenceUnit.required}")
    @Pattern(regexp = "^(g|ml)$", message = "{validation.referenceUnit.invalid}")
    private String baseUnit = "g";

    @NotNull(message = "{validation.calories.required}")
    @DecimalMin(value = "0", message = "{validation.calories.nonnegative}")
    private Double caloriesPer100;

    @NotNull(message = "{validation.protein.required}")
    @DecimalMin(value = "0", message = "{validation.protein.nonnegative}")
    private Double proteinPer100;

    @NotNull(message = "{validation.carbs.required}")
    @DecimalMin(value = "0", message = "{validation.carbs.nonnegative}")
    private Double carbohydratePer100;

    @NotNull(message = "{validation.fat.required}")
    @DecimalMin(value = "0", message = "{validation.fat.nonnegative}")
    private Double fatPer100;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getBaseUnit() { return baseUnit; }
    public void setBaseUnit(String baseUnit) { this.baseUnit = baseUnit; }
    public Double getCaloriesPer100() { return caloriesPer100; }
    public void setCaloriesPer100(Double caloriesPer100) { this.caloriesPer100 = caloriesPer100; }
    public Double getProteinPer100() { return proteinPer100; }
    public void setProteinPer100(Double proteinPer100) { this.proteinPer100 = proteinPer100; }
    public Double getCarbohydratePer100() { return carbohydratePer100; }
    public void setCarbohydratePer100(Double carbohydratePer100) { this.carbohydratePer100 = carbohydratePer100; }
    public Double getFatPer100() { return fatPer100; }
    public void setFatPer100(Double fatPer100) { this.fatPer100 = fatPer100; }
}
