package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
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
