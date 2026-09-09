package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class FoodEntryFormValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void acceptsGramAndMilliliterOnly() {
        FoodEntryForm form = validForm();

        form.setServingUnit("g");
        assertThat(validator.validate(form)).isEmpty();

        form.setServingUnit("ml");
        assertThat(validator.validate(form)).isEmpty();
    }

    @Test
    void rejectsOtherServingUnits() {
        FoodEntryForm form = validForm();
        form.setServingUnit("plate");

        assertThat(validator.validate(form))
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("servingUnit"));
    }

    private FoodEntryForm validForm() {
        FoodEntryForm form = new FoodEntryForm();
        form.setFoodName("ข้าว");
        form.setCalories(130.0);
        form.setProteinGrams(2.7);
        form.setCarbohydrateGrams(28.0);
        form.setFatGrams(0.3);
        return form;
    }
}
