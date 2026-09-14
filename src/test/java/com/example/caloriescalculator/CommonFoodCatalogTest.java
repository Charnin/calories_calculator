package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonFoodCatalogTest {
    @Autowired
    private CommonFoodRepository repository;

    @Test
    void seedsTheCuratedThaiFoodCatalog() {
        assertThat(repository.count()).isEqualTo(94);
        assertThat(repository.findByFoodCode("THT54"))
                .get()
                .satisfies(food -> {
                    assertThat(food.getNameTh()).isEqualTo("ข้าวราดไก่ผัดกะเพรา");
                    assertThat(food.getCaloriesPer100()).isEqualTo(188.0);
                    assertThat(food.getProteinPer100()).isEqualTo(8.6);
                    assertThat(food.getCarbohydratePer100()).isEqualTo(23.6);
                    assertThat(food.getFatPer100()).isEqualTo(6.6);
                    assertThat(food.getServingSize()).isEqualTo(300.0);
                    assertThat(food.getServingLabelTh()).isEqualTo("1 จาน");
                });
    }
}
