package com.example.caloriescalculator;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteFoodRepository extends JpaRepository<FavoriteFood, Long> {
    Optional<FavoriteFood> findByUserAndFoodKey(AppUser user, String foodKey);
    List<FavoriteFood> findByUserOrderByCreatedAtDesc(AppUser user);
}
