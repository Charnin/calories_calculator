package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomFoodForm {
    @NotBlank(message = "กรุณากรอกชื่ออาหาร")
    @Size(max = 160, message = "ชื่ออาหารต้องไม่เกิน 160 ตัวอักษร")
    private String name;

    @Size(max = 120, message = "ยี่ห้อต้องไม่เกิน 120 ตัวอักษร")
    private String brand = "";

    @NotBlank(message = "กรุณาเลือกหน่วยอ้างอิง")
    @Pattern(regexp = "^(g|ml)$", message = "หน่วยอ้างอิงต้องเป็นกรัมหรือมิลลิลิตร")
    private String baseUnit = "g";

    @NotNull(message = "กรุณากรอกแคลอรี่")
    @DecimalMin(value = "0", message = "แคลอรี่ต้องไม่ติดลบ")
    private Double caloriesPer100;

    @NotNull(message = "กรุณากรอกโปรตีน")
    @DecimalMin(value = "0", message = "โปรตีนต้องไม่ติดลบ")
    private Double proteinPer100;

    @NotNull(message = "กรุณากรอกคาร์โบไฮเดรต")
    @DecimalMin(value = "0", message = "คาร์โบไฮเดรตต้องไม่ติดลบ")
    private Double carbohydratePer100;

    @NotNull(message = "กรุณากรอกไขมัน")
    @DecimalMin(value = "0", message = "ไขมันต้องไม่ติดลบ")
    private Double fatPer100;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getBaseUnit() { return baseUnit; }
    public void setBaseUnit(String baseUnit) { this.baseUnit = baseUnit; }
    public Double getCaloriesPer100() { return caloriesPer100; }
    public void setCaloriesPer100(Double caloriesPer100) { this.caloriesPer100 = caloriesPer100; }
    public Double getProteinPer100() { return proteinPer100; }
    public void setProteinPer100(Double proteinPer100) { this.proteinPer100 = proteinPer100; }
    public Double getCarbohydratePer100() { return carbohydratePer100; }
    public void setCarbohydratePer100(Double carbohydratePer100) { this.carbohydratePer100 = carbohydratePer100; }
    public Double getFatPer100() { return fatPer100; }
    public void setFatPer100(Double fatPer100) { this.fatPer100 = fatPer100; }
}
