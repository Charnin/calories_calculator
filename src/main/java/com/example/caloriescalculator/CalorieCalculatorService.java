package com.example.caloriescalculator;

import org.springframework.stereotype.Service;

@Service
public class CalorieCalculatorService {

    public CalculationResult calculate(UserProfileForm profile) {
        double bmr = calculateBmr(profile);
        double maintenanceCalories = bmr * profile.getActivityLevel();
        double calorieTarget = maintenanceCalories * goalMultiplier(profile.getGoal());
        double proteinGrams = profile.getWeightKg() * ("lose".equals(profile.getGoal()) ? 2.0 : 1.6);
        double fatGrams = calorieTarget * 0.25 / 9;
        double carbohydrateGrams = Math.max(0, (calorieTarget - proteinGrams * 4 - fatGrams * 9) / 4);

        return new CalculationResult(
                Math.round(bmr),
                Math.round(maintenanceCalories),
                Math.round(calorieTarget),
                Math.round(proteinGrams),
                Math.round(carbohydrateGrams),
                Math.round(fatGrams),
                profile.getBodyFatPercent() == null ? "Mifflin–St Jeor" : "Katch–McArdle");
    }

    private double calculateBmr(UserProfileForm profile) {
        if (profile.getBodyFatPercent() != null) {
            double leanBodyMass = profile.getWeightKg() * (1 - profile.getBodyFatPercent() / 100);
            return 370 + (21.6 * leanBodyMass);
        }
        double base = (10 * profile.getWeightKg()) + (6.25 * profile.getHeightCm()) - (5 * profile.getAge());
        return "male".equals(profile.getSex()) ? base + 5 : base - 161;
    }

    private double goalMultiplier(String goal) {
        return switch (goal) {
            case "lose" -> 0.85;
            case "gain" -> 1.10;
            default -> 1.0;
        };
    }
}
