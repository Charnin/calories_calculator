package com.example.caloriescalculator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "common_foods", uniqueConstraints = @UniqueConstraint(columnNames = "food_code"))
public class CommonFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_code", nullable = false, length = 32)
    private String foodCode;
    @Column(nullable = false)
    private String nameTh;
    @Column(nullable = false)
    private String nameEn;
    @Column(nullable = false, length = 48)
    private String category;
    @Column(nullable = false, length = 8)
    private String baseUnit;
    @Column(nullable = false)
    private Double caloriesPer100;
    @Column(nullable = false)
    private Double proteinPer100;
    @Column(nullable = false)
    private Double carbohydratePer100;
    @Column(nullable = false)
    private Double fatPer100;
    private Double servingSize;
    private String servingLabelTh;
    private String servingLabelEn;
    @Column(nullable = false)
    private String sourceName;
    @Column(nullable = false, length = 500)
    private String sourceUrl;

    protected CommonFood() { }

    public CommonFood(String foodCode, String nameTh, String nameEn, String category, String baseUnit,
                      Double caloriesPer100, Double proteinPer100, Double carbohydratePer100,
                      Double fatPer100, Double servingSize, String servingLabelTh, String servingLabelEn,
                      String sourceName, String sourceUrl) {
        this.foodCode = foodCode;
        this.nameTh = nameTh;
        this.nameEn = nameEn;
        this.category = category;
        this.baseUnit = baseUnit;
        this.caloriesPer100 = caloriesPer100;
        this.proteinPer100 = proteinPer100;
        this.carbohydratePer100 = carbohydratePer100;
        this.fatPer100 = fatPer100;
        this.servingSize = servingSize;
        this.servingLabelTh = servingLabelTh;
        this.servingLabelEn = servingLabelEn;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
    }

    public void updateFrom(CommonFood source) {
        this.nameTh = source.nameTh;
        this.nameEn = source.nameEn;
        this.category = source.category;
        this.baseUnit = source.baseUnit;
        this.caloriesPer100 = source.caloriesPer100;
        this.proteinPer100 = source.proteinPer100;
        this.carbohydratePer100 = source.carbohydratePer100;
        this.fatPer100 = source.fatPer100;
        this.servingSize = source.servingSize;
        this.servingLabelTh = source.servingLabelTh;
        this.servingLabelEn = source.servingLabelEn;
        this.sourceName = source.sourceName;
        this.sourceUrl = source.sourceUrl;
    }

    public Long getId() { return id; }
    public String getFoodCode() { return foodCode; }
    public String getNameTh() { return nameTh; }
    public String getNameEn() { return nameEn; }
    public String getCategory() { return category; }
    public String getBaseUnit() { return baseUnit; }
    public Double getCaloriesPer100() { return caloriesPer100; }
    public Double getProteinPer100() { return proteinPer100; }
    public Double getCarbohydratePer100() { return carbohydratePer100; }
    public Double getFatPer100() { return fatPer100; }
    public Double getServingSize() { return servingSize == null ? 100.0 : servingSize; }
    public String getServingLabelTh() { return servingLabelTh == null ? "100 กรัม" : servingLabelTh; }
    public String getServingLabelEn() { return servingLabelEn == null ? "100 grams" : servingLabelEn; }
    public String getSourceName() { return sourceName; }
    public String getSourceUrl() { return sourceUrl; }
    public boolean isEstimated() { return sourceName != null && sourceName.startsWith("ค่าประมาณ"); }
}
