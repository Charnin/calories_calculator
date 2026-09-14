package com.example.caloriescalculator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {
    List<FoodEntry> findByUserAndLogDateOrderByCreatedAtAsc(AppUser user, LocalDate logDate);
    List<FoodEntry> findByUserAndLogDateAndMealTypeOrderByCreatedAtAsc(
            AppUser user, LocalDate logDate, String mealType);
    List<FoodEntry> findByUserAndLogDateBetweenOrderByLogDateAscCreatedAtAsc(
            AppUser user, LocalDate startDate, LocalDate endDate);
    Optional<FoodEntry> findByIdAndUser(Long id, AppUser user);
    List<FoodEntry> findTop30ByUserOrderByCreatedAtDesc(AppUser user);
}
