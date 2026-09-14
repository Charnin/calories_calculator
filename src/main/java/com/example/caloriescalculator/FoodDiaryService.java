package com.example.caloriescalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoodDiaryService {
    private final FoodEntryRepository foodEntryRepository;
    private final CalculationRecordRepository calculationRecordRepository;

    public FoodDiaryService(FoodEntryRepository foodEntryRepository,
                            CalculationRecordRepository calculationRecordRepository) {
        this.foodEntryRepository = foodEntryRepository;
        this.calculationRecordRepository = calculationRecordRepository;
    }

    public List<FoodEntry> entriesFor(AppUser user, LocalDate date) {
        return foodEntryRepository.findByUserAndLogDateOrderByCreatedAtAsc(user, date);
    }

    @Transactional
    public FoodEntry add(AppUser user, FoodEntryForm form) {
        return foodEntryRepository.save(new FoodEntry(user, form));
    }

    public FoodEntry requireEntry(AppUser user, Long entryId) {
        return foodEntryRepository.findByIdAndUser(entryId, user)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบรายการอาหาร"));
    }

    @Transactional
    public void update(AppUser user, Long entryId, FoodEntryForm form) {
        requireEntry(user, entryId).update(form);
    }

    @Transactional
    public void adjustPortions(AppUser user, Long entryId, double delta) {
        if (delta != -1.0 && delta != 1.0) {
            throw new IllegalArgumentException("Unsupported portion adjustment");
        }
        requireEntry(user, entryId).adjustPortions(delta);
    }

    public Set<String> recentFoodNames(AppUser user) {
        return foodEntryRepository.findTop30ByUserOrderByCreatedAtDesc(user).stream()
                .map(FoodEntry::getFoodName)
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional
    public void delete(AppUser user, Long entryId) {
        foodEntryRepository.findByIdAndUser(entryId, user).ifPresent(foodEntryRepository::delete);
    }

    public DiarySummary summarize(AppUser user, LocalDate date, List<FoodEntry> entries) {
        long consumedCalories = Math.round(entries.stream().mapToDouble(FoodEntry::getCalories).sum());
        long consumedProtein = Math.round(entries.stream().mapToDouble(FoodEntry::getProteinGrams).sum());
        long consumedCarbohydrate = Math.round(entries.stream().mapToDouble(FoodEntry::getCarbohydrateGrams).sum());
        long consumedFat = Math.round(entries.stream().mapToDouble(FoodEntry::getFatGrams).sum());

        return calculationRecordRepository
                .findFirstByUserAndPlanDateLessThanEqualOrderByPlanDateDescCalculatedAtDesc(user, date)
                .map(plan -> new DiarySummary(
                        plan.getCalorieTarget(), consumedCalories, plan.getCalorieTarget() - consumedCalories,
                        plan.getProteinGrams(), consumedProtein, plan.getProteinGrams() - consumedProtein,
                        plan.getCarbohydrateGrams(), consumedCarbohydrate, plan.getCarbohydrateGrams() - consumedCarbohydrate,
                        plan.getFatGrams(), consumedFat, plan.getFatGrams() - consumedFat, true))
                .orElseGet(() -> new DiarySummary(
                        0, consumedCalories, -consumedCalories,
                        0, consumedProtein, -consumedProtein,
                        0, consumedCarbohydrate, -consumedCarbohydrate,
                        0, consumedFat, -consumedFat, false));
    }
}
