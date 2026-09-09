package com.example.caloriescalculator;

import java.util.List;

public record ProgressReport(
        List<DailyProgress> days,
        long averageCalories,
        long averageProtein,
        Double startWeightKg,
        Double endWeightKg) {
    public Double weightChangeKg() {
        if (startWeightKg == null || endWeightKg == null) return null;
        return Math.round((endWeightKg - startWeightKg) * 10.0) / 10.0;
    }

    public int weightPercent(Double weightKg) {
        if (weightKg == null) return 0;
        double minimum = days.stream().map(DailyProgress::weightKg).filter(java.util.Objects::nonNull)
                .mapToDouble(Double::doubleValue).min().orElse(weightKg);
        double maximum = days.stream().map(DailyProgress::weightKg).filter(java.util.Objects::nonNull)
                .mapToDouble(Double::doubleValue).max().orElse(weightKg);
        if (maximum == minimum) return 55;
        return 20 + (int) Math.round((weightKg - minimum) * 75 / (maximum - minimum));
    }
}
