package com.example.caloriescalculator;

import java.time.LocalDate;

public record DailyProgress(
        LocalDate date,
        long calorieTarget,
        long consumedCalories,
        long proteinTarget,
        long consumedProtein,
        Double weightKg,
        int caloriePercent,
        int proteinPercent,
        boolean overCalories) {
}
