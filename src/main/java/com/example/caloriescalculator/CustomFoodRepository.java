package com.example.caloriescalculator;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFoodRepository extends JpaRepository<CustomFood, Long> {
    List<CustomFood> findByUserOrderByNameAsc(AppUser user);
    Optional<CustomFood> findByIdAndUser(Long id, AppUser user);
}
