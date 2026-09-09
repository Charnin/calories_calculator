package com.example.caloriescalculator;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationRecordRepository extends JpaRepository<CalculationRecord, Long> {
    List<CalculationRecord> findTop10ByUserOrderByCalculatedAtDesc(AppUser user);
    Optional<CalculationRecord> findFirstByUserOrderByCalculatedAtDesc(AppUser user);
    Optional<CalculationRecord> findFirstByUserAndPlanDateLessThanEqualOrderByPlanDateDescCalculatedAtDesc(
            AppUser user, LocalDate planDate);
}
