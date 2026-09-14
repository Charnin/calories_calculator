package com.example.caloriescalculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FavoriteFoodServiceTest {
    @Autowired private AppUserRepository userRepository;
    @Autowired private FavoriteFoodService favoriteFoodService;

    @Test
    void togglesFavoritesForEachUser() {
        AppUser user = userRepository.save(new AppUser("Favorite Owner", "favorite-owner@example.com", "hash"));

        assertThat(favoriteFoodService.toggle(user, "common-THT54")).isTrue();
        assertThat(favoriteFoodService.keysFor(user)).containsExactly("common-THT54");
        assertThat(favoriteFoodService.toggle(user, "common-THT54")).isFalse();
        assertThat(favoriteFoodService.keysFor(user)).isEmpty();
    }

    @Test
    void rejectsUnknownFavoriteKeys() {
        AppUser user = userRepository.save(new AppUser("Invalid Favorite", "invalid-favorite@example.com", "hash"));
        assertThatThrownBy(() -> favoriteFoodService.toggle(user, "../bad-key"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
