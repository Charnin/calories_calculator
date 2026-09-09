package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

class BodyFatEstimatorServiceTest {
    private final BodyFatEstimatorService service = new BodyFatEstimatorService();

    @Test
    void estimatesMaleBodyFatFromTapeMeasurementsInCentimeters() {
        UserProfileForm profile = validProfile("male");
        BindingResult errors = new BeanPropertyBindingResult(profile, "profile");

        boolean estimated = service.prepareBodyFat(profile, errors);

        assertThat(errors.hasErrors()).isFalse();
        assertThat(estimated).isTrue();
        assertThat(profile.getBodyFatPercent()).isBetween(14.0, 17.0);
    }

    @Test
    void requiresHipMeasurementForWomen() {
        UserProfileForm profile = validProfile("female");
        BindingResult errors = new BeanPropertyBindingResult(profile, "profile");

        boolean estimated = service.prepareBodyFat(profile, errors);

        assertThat(estimated).isFalse();
        assertThat(errors.hasFieldErrors("hipCm")).isTrue();
    }

    private UserProfileForm validProfile(String sex) {
        UserProfileForm profile = new UserProfileForm();
        profile.setSex(sex);
        profile.setHeightCm(175.0);
        profile.setBodyFatMode("tape");
        profile.setNeckCm(37.0);
        profile.setWaistCm(82.0);
        return profile;
    }
}
