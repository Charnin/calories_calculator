package com.example.caloriescalculator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "custom_foods")
public class CustomFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private String name;
    private String brand;
    private String baseUnit;
    private Double caloriesPer100;
    private Double proteinPer100;
    private Double carbohydratePer100;
    private Double fatPer100;
    private Instant createdAt;

    protected CustomFood() { }

    public CustomFood(AppUser user, CustomFoodForm form) {
        this.user = user;
        update(form);
        this.createdAt = Instant.now();
    }

    public void update(CustomFoodForm form) {
        this.name = form.getName().trim();
        this.brand = form.getBrand() == null ? "" : form.getBrand().trim();
        this.baseUnit = form.getBaseUnit();
        this.caloriesPer100 = form.getCaloriesPer100();
        this.proteinPer100 = form.getProteinPer100();
        this.carbohydratePer100 = form.getCarbohydratePer100();
        this.fatPer100 = form.getFatPer100();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getBaseUnit() { return baseUnit == null ? "g" : baseUnit; }
    public String getBaseUnitLabel() { return "ml".equals(getBaseUnit()) ? "มิลลิลิตร" : "กรัม"; }
    public Double getCaloriesPer100() { return caloriesPer100; }
    public Double getProteinPer100() { return proteinPer100; }
    public Double getCarbohydratePer100() { return carbohydratePer100; }
    public Double getFatPer100() { return fatPer100; }
}
