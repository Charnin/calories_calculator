package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

class RegistrationFormValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void rejectsThaiCharactersInPassword() {
        RegistrationForm form = validForm();
        form.setPassword("passwordไทย123");

        assertThat(validator.validate(form))
                .anyMatch(violation -> violation.getPropertyPath().toString().equals("password"));
    }

    @Test
    void acceptsEnglishNumbersAndSymbols() {
        RegistrationForm form = validForm();
        form.setPassword("Strong-pass_123");

        assertThat(validator.validate(form)).isEmpty();
    }

    private RegistrationForm validForm() {
        RegistrationForm form = new RegistrationForm();
        form.setDisplayName("Test User");
        form.setEmail("test@example.com");
        form.setPassword("password123");
        form.setConfirmPassword("password123");
        return form;
    }
}
