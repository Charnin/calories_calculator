package com.example.caloriescalculator;

public record DiarySummary(
        long calorieTarget, long consumedCalories, long remainingCalories,
        long proteinTarget, long consumedProtein, long remainingProtein,
        long carbohydrateTarget, long consumedCarbohydrate, long remainingCarbohydrate,
        long fatTarget, long consumedFat, long remainingFat,
        boolean hasPlan) {
}
