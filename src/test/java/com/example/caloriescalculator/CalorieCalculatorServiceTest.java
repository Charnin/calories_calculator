package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CalorieCalculatorServiceTest {
    private final CalorieCalculatorService service = new CalorieCalculatorService();

    @Test
    void calculatesMaintenancePlanWithMifflinStJeor() {
        UserProfileForm profile = profile("male", "maintain");

        CalculationResult result = service.calculate(profile);

        assertThat(result.bmr()).isEqualTo(1649);
        assertThat(result.maintenanceCalories()).isEqualTo(2556);
        assertThat(result.calorieTarget()).isEqualTo(2556);
        assertThat(result.proteinGrams()).isEqualTo(112);
        assertThat(result.calculationMethod()).isEqualTo("Mifflin–St Jeor");
    }

    @Test
    void appliesCalorieDeficitForFatLoss() {
        UserProfileForm profile = profile("female", "lose");

        CalculationResult result = service.calculate(profile);

        assertThat(result.calorieTarget()).isLessThan(result.maintenanceCalories());
        assertThat(result.proteinGrams()).isEqualTo(140);
    }

    @Test
    void usesBodyFatFormulaWhenBodyFatIsProvided() {
        UserProfileForm profile = profile("male", "gain");
        profile.setBodyFatPercent(20.0);

        CalculationResult result = service.calculate(profile);

        assertThat(result.bmr()).isEqualTo(1580);
        assertThat(result.calculationMethod()).isEqualTo("Katch–McArdle");
    }

    private UserProfileForm profile(String sex, String goal) {
        UserProfileForm profile = new UserProfileForm();
        profile.setAge(30);
        profile.setSex(sex);
        profile.setWeightKg(70.0);
        profile.setHeightCm(175.0);
        profile.setActivityLevel(1.55);
        profile.setGoal(goal);
        return profile;
    }
}
