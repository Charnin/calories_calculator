package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProgressServiceTest {
    @Autowired private AppUserRepository userRepository;
    @Autowired private CalculationRecordRepository recordRepository;
    @Autowired private FoodDiaryService diaryService;
    @Autowired private ProgressService progressService;

    @Test
    void buildsSevenDayReportWithCurrentTargets() {
        AppUser user = userRepository.save(new AppUser("Progress", "report@example.com", "hash"));
        UserProfileForm profile = profile();
        recordRepository.save(new CalculationRecord(user, profile,
                new CalculationResult(1500, 2000, 1800, 120, 200, 60, "test")));
        diaryService.add(user, food(900, 60));

        ProgressReport report = progressService.report(user, 7);
        DailyProgress today = report.days().getLast();

        assertThat(report.days()).hasSize(7);
        assertThat(today.consumedCalories()).isEqualTo(900);
        assertThat(today.calorieTarget()).isEqualTo(1800);
        assertThat(today.caloriePercent()).isEqualTo(50);
        assertThat(today.proteinPercent()).isEqualTo(50);
        assertThat(report.endWeightKg()).isEqualTo(70.0);
    }

    private FoodEntryForm food(double calories, double protein) {
        FoodEntryForm form = new FoodEntryForm();
        form.setFoodName("อาหารทดสอบ");
        form.setServingQuantity(100.0);
        form.setServingUnit("g");
        form.setCalories(calories);
        form.setProteinGrams(protein);
        form.setCarbohydrateGrams(20.0);
        form.setFatGrams(5.0);
        return form;
    }

    private UserProfileForm profile() {
        UserProfileForm form = new UserProfileForm();
        form.setAge(30);
        form.setSex("male");
        form.setWeightKg(70.0);
        form.setHeightCm(175.0);
        form.setActivityLevel(1.55);
        form.setGoal("maintain");
        return form;
    }
}
