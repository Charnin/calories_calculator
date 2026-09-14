package com.example.caloriescalculator;

import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteFoodService {
    private final FavoriteFoodRepository repository;

    public FavoriteFoodService(FavoriteFoodRepository repository) {
        this.repository = repository;
    }

    public Set<String> keysFor(AppUser user) {
        return repository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(FavoriteFood::getFoodKey)
                .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional
    public boolean toggle(AppUser user, String foodKey) {
        if (foodKey == null || !foodKey.matches("^(common-[A-Za-z0-9_-]{1,32}|custom-[0-9]+)$")) {
            throw new IllegalArgumentException("Invalid food key");
        }
        return repository.findByUserAndFoodKey(user, foodKey)
                .map(favorite -> { repository.delete(favorite); return false; })
                .orElseGet(() -> { repository.save(new FavoriteFood(user, foodKey)); return true; });
    }
}
