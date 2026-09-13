package com.example.caloriescalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProgressService {
    private final FoodEntryRepository foodEntryRepository;
    private final CalculationRecordRepository calculationRecordRepository;

    public ProgressService(FoodEntryRepository foodEntryRepository,
                           CalculationRecordRepository calculationRecordRepository) {
        this.foodEntryRepository = foodEntryRepository;
        this.calculationRecordRepository = calculationRecordRepository;
    }

    public ProgressReport report(AppUser user, int numberOfDays) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(numberOfDays - 1L);
        Map<LocalDate, List<FoodEntry>> entriesByDate = foodEntryRepository
                .findByUserAndLogDateBetweenOrderByLogDateAscCreatedAtAsc(user, start, end)
                .stream().collect(Collectors.groupingBy(FoodEntry::getLogDate));

        List<DailyProgress> days = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            List<FoodEntry> entries = entriesByDate.getOrDefault(date, List.of());
            long calories = Math.round(entries.stream().mapToDouble(FoodEntry::getCalories).sum());
            long protein = Math.round(entries.stream().mapToDouble(FoodEntry::getProteinGrams).sum());
            CalculationRecord plan = calculationRecordRepository
                    .findFirstByUserAndPlanDateLessThanEqualOrderByPlanDateDescCalculatedAtDesc(user, date)
                    .orElse(null);
            long calorieTarget = plan == null ? 0 : plan.getCalorieTarget();
            long proteinTarget = plan == null ? 0 : plan.getProteinGrams();
            days.add(new DailyProgress(date, calorieTarget, calories, proteinTarget, protein,
                    plan == null ? null : plan.getWeightKg(), percent(calories, calorieTarget),
                    percent(protein, proteinTarget), calorieTarget > 0 && calories > calorieTarget));
        }

        List<DailyProgress> logged = days.stream()
                .filter(day -> !entriesByDate.getOrDefault(day.date(), List.of()).isEmpty())
                .toList();
        long averageCalories = Math.round(logged.stream().mapToLong(DailyProgress::consumedCalories).average().orElse(0));
        long averageProtein = Math.round(logged.stream().mapToLong(DailyProgress::consumedProtein).average().orElse(0));
        long calorieTargetDays = logged.stream().filter(day -> day.calorieTarget() > 0
                && Math.abs(day.consumedCalories() - day.calorieTarget()) <= day.calorieTarget() * 0.10).count();
        long proteinTargetDays = logged.stream().filter(day -> day.proteinTarget() > 0
                && day.consumedProtein() >= day.proteinTarget() * 0.90).count();
        Double startWeight = days.stream().map(DailyProgress::weightKg).filter(java.util.Objects::nonNull).findFirst().orElse(null);
        Double endWeight = days.reversed().stream().map(DailyProgress::weightKg).filter(java.util.Objects::nonNull).findFirst().orElse(null);
        return new ProgressReport(List.copyOf(days), averageCalories, averageProtein, logged.size(),
                calorieTargetDays, proteinTargetDays, startWeight, endWeight);
    }

    private int percent(long value, long target) {
        if (target <= 0) return 0;
        return (int) Math.min(100, Math.round(value * 100.0 / target));
    }
}
