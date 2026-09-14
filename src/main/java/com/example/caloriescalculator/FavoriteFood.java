package com.example.caloriescalculator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;

@Entity
@Table(name = "favorite_foods", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "food_key"}))
public class FavoriteFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "food_key", nullable = false, length = 80)
    private String foodKey;
    private Instant createdAt;

    protected FavoriteFood() { }

    public FavoriteFood(AppUser user, String foodKey) {
        this.user = user;
        this.foodKey = foodKey;
        this.createdAt = Instant.now();
    }

    public String getFoodKey() { return foodKey; }
}
