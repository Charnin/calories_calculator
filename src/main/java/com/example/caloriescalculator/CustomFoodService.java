package com.example.caloriescalculator;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomFoodService {
    private final CustomFoodRepository repository;

    public CustomFoodService(CustomFoodRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void add(AppUser user, CustomFoodForm form) {
        repository.save(new CustomFood(user, form));
    }

    @Transactional
    public void delete(AppUser user, Long id) {
        repository.findByIdAndUser(id, user).ifPresent(repository::delete);
    }

    @Transactional
    public void update(AppUser user, Long id, CustomFoodForm form) {
        repository.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบอาหารของฉัน"))
                .update(form);
    }

    public List<CustomFood> list(AppUser user) {
        return repository.findTop20ByUserOrderByNameAsc(user);
    }

}
