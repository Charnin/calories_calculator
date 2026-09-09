package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomFoodServiceTest {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private CustomFoodRepository foodRepository;

    @Autowired
    private CustomFoodService customFoodService;

    @Test
    void customFoodsArePrivateToTheirOwner() {
        AppUser owner = userRepository.save(new AppUser("Owner", "owner@example.com", "hash"));
        AppUser anotherUser = userRepository.save(new AppUser("Other", "other@example.com", "hash"));

        CustomFoodForm form = food("อกไก่ย่าง", 165, 31, 0, 3.6);
        customFoodService.add(owner, form);

        assertThat(customFoodService.list(owner))
                .singleElement()
                .satisfies(food -> assertThat(food.getName()).isEqualTo("อกไก่ย่าง"));
        assertThat(customFoodService.list(anotherUser)).isEmpty();
    }

    @Test
    void anotherUserCannotDeleteOwnersFood() {
        AppUser owner = userRepository.save(new AppUser("Owner", "owner2@example.com", "hash"));
        AppUser anotherUser = userRepository.save(new AppUser("Other", "other2@example.com", "hash"));
        customFoodService.add(owner, food("ข้าวกล้อง", 111, 2.6, 23, 0.9));
        Long foodId = customFoodService.list(owner).getFirst().getId();

        customFoodService.delete(anotherUser, foodId);

        assertThat(foodRepository.findById(foodId)).isPresent();
    }

    @Test
    void ownerCanUpdateLiquidFoodUnitAndNutrition() {
        AppUser owner = userRepository.save(new AppUser("Owner", "liquid-owner@example.com", "hash"));
        customFoodService.add(owner, food("นม", 60, 3, 5, 3));
        CustomFood saved = customFoodService.list(owner).getFirst();
        CustomFoodForm update = food("นมไขมันต่ำ", 45, 3.4, 5, 1.5);
        update.setBaseUnit("ml");

        customFoodService.update(owner, saved.getId(), update);

        CustomFood updated = customFoodService.list(owner).getFirst();
        assertThat(updated.getName()).isEqualTo("นมไขมันต่ำ");
        assertThat(updated.getBaseUnit()).isEqualTo("ml");
        assertThat(updated.getBaseUnitLabel()).isEqualTo("มิลลิลิตร");
    }

    private CustomFoodForm food(String name, double calories, double protein, double carbs, double fat) {
        CustomFoodForm form = new CustomFoodForm();
        form.setName(name);
        form.setCaloriesPer100(calories);
        form.setProteinPer100(protein);
        form.setCarbohydratePer100(carbs);
        form.setFatPer100(fat);
        return form;
    }
}
