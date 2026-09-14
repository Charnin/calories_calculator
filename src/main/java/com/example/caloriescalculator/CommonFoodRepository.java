package com.example.caloriescalculator;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonFoodRepository extends JpaRepository<CommonFood, Long> {
    Optional<CommonFood> findByFoodCode(String foodCode);
    List<CommonFood> findAllByOrderByNameThAsc();
}
