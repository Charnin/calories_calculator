package com.example.caloriescalculator;

public record CalculationResult(
        long bmr,
        long maintenanceCalories,
        long calorieTarget,
        long proteinGrams,
        long carbohydrateGrams,
        long fatGrams,
        String calculationMethod) {
}
