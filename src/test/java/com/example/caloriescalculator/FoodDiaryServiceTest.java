package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FoodDiaryServiceTest {
    @Autowired private AppUserRepository userRepository;
    @Autowired private FoodDiaryService diaryService;

    @Test
    void ownerCanEditEntryButAnotherUserCannot() {
        AppUser owner = userRepository.save(new AppUser("Owner", "diary-owner@example.com", "hash"));
        AppUser other = userRepository.save(new AppUser("Other", "diary-other@example.com", "hash"));
        FoodEntryForm original = food("ข้าว", 100, 130);
        diaryService.add(owner, original);
        Long id = diaryService.entriesFor(owner, LocalDate.now()).getFirst().getId();

        assertThatThrownBy(() -> diaryService.update(other, id, food("อาหารของคนอื่น", 100, 1)))
                .isInstanceOf(IllegalArgumentException.class);

        diaryService.update(owner, id, food("ข้าวกล้อง", 150, 195));
        FoodEntry updated = diaryService.entriesFor(owner, LocalDate.now()).getFirst();
        assertThat(updated.getFoodName()).isEqualTo("ข้าวกล้อง");
        assertThat(updated.getServingQuantity()).isEqualTo(150);
        assertThat(updated.getCalories()).isEqualTo(195);
    }

    @Test
    void copiesOnlyTheSelectedMealOwnedByTheUser() {
        AppUser owner = userRepository.save(new AppUser("Copy Owner", "copy-owner@example.com", "hash"));
        AppUser other = userRepository.save(new AppUser("Copy Other", "copy-other@example.com", "hash"));
        LocalDate sourceDate = LocalDate.now().minusDays(1);
        LocalDate targetDate = LocalDate.now();
        FoodEntryForm breakfast = food("ไข่", 100, 150);
        breakfast.setLogDate(sourceDate);
        breakfast.setMealType("breakfast");
        FoodEntryForm lunch = food("ข้าว", 100, 200);
        lunch.setLogDate(sourceDate);
        lunch.setMealType("lunch");
        FoodEntryForm otherBreakfast = food("อาหารของคนอื่น", 100, 300);
        otherBreakfast.setLogDate(sourceDate);
        otherBreakfast.setMealType("breakfast");
        diaryService.add(owner, breakfast);
        diaryService.add(owner, lunch);
        diaryService.add(other, otherBreakfast);

        int copied = diaryService.copyMeal(owner, sourceDate, targetDate, "breakfast");

        assertThat(copied).isEqualTo(1);
        assertThat(diaryService.entriesFor(owner, targetDate))
                .singleElement()
                .satisfies(entry -> assertThat(entry.getFoodName()).isEqualTo("ไข่"));
        assertThat(diaryService.entriesFor(other, targetDate)).isEmpty();
    }

    @Test
    void adjustsAPortionAndSummarizesMeals() {
        AppUser owner = userRepository.save(new AppUser("Portion Owner", "portion-owner@example.com", "hash"));
        FoodEntryForm breakfast = food("ไข่", 100, 150);
        breakfast.setMealType("breakfast");
        diaryService.add(owner, breakfast);
        FoodEntry entry = diaryService.entriesFor(owner, LocalDate.now()).getFirst();

        diaryService.scale(owner, entry.getId(), 1.5);

        FoodEntry adjusted = diaryService.entriesFor(owner, LocalDate.now()).getFirst();
        assertThat(adjusted.getServingQuantity()).isEqualTo(150);
        assertThat(adjusted.getCalories()).isEqualTo(225);
        assertThat(diaryService.mealSummaries(List.of(adjusted)))
                .first()
                .satisfies(meal -> {
                    assertThat(meal.mealType()).isEqualTo("breakfast");
                    assertThat(meal.calories()).isEqualTo(225);
                    assertThat(meal.protein()).isEqualTo(15);
                });
    }

    private FoodEntryForm food(String name, double quantity, double calories) {
        FoodEntryForm form = new FoodEntryForm();
        form.setFoodName(name);
        form.setServingQuantity(quantity);
        form.setServingUnit("g");
        form.setCalories(calories);
        form.setProteinGrams(10.0);
        form.setCarbohydrateGrams(20.0);
        form.setFatGrams(5.0);
        return form;
    }

}
