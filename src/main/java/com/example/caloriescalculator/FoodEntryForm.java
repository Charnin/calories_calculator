package com.example.caloriescalculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class FoodEntryForm {
    @NotNull(message = "กรุณาเลือกวันที่")
    @PastOrPresent(message = "ยังไม่สามารถบันทึกอาหารล่วงหน้าได้")
    private LocalDate logDate = LocalDate.now();

    @NotBlank(message = "กรุณาเลือกมื้ออาหาร")
    private String mealType = "breakfast";

    @NotBlank(message = "กรุณากรอกชื่ออาหาร")
    @Size(max = 160, message = "ชื่ออาหารต้องไม่เกิน 160 ตัวอักษร")
    private String foodName;

    @NotNull(message = "กรุณากรอกจำนวน")
    @DecimalMin(value = "0.01", message = "จำนวนต้องมากกว่า 0")
    private Double servingQuantity = 1.0;

    @NotBlank(message = "กรุณาเลือกหน่วยบริโภค")
    @Pattern(regexp = "^(g|ml)$", message = "หน่วยบริโภคต้องเป็นกรัมหรือมิลลิลิตร")
    private String servingUnit = "g";

    @NotNull(message = "กรุณากรอกแคลอรี่")
    @DecimalMin(value = "0.0", message = "แคลอรี่ต้องไม่ติดลบ")
    private Double calories;

    @NotNull(message = "กรุณากรอกโปรตีน")
    @DecimalMin(value = "0.0", message = "โปรตีนต้องไม่ติดลบ")
    private Double proteinGrams = 0.0;

    @NotNull(message = "กรุณากรอกคาร์โบไฮเดรต")
    @DecimalMin(value = "0.0", message = "คาร์โบไฮเดรตต้องไม่ติดลบ")
    private Double carbohydrateGrams = 0.0;

    @NotNull(message = "กรุณากรอกไขมัน")
    @DecimalMin(value = "0.0", message = "ไขมันต้องไม่ติดลบ")
    private Double fatGrams = 0.0;

    public LocalDate getLogDate() { return logDate; }
    public void setLogDate(LocalDate logDate) { this.logDate = logDate; }
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }
    public Double getServingQuantity() { return servingQuantity; }
    public void setServingQuantity(Double servingQuantity) { this.servingQuantity = servingQuantity; }
    public String getServingUnit() { return servingUnit; }
    public void setServingUnit(String servingUnit) { this.servingUnit = servingUnit; }
    public Double getCalories() { return calories; }
    public void setCalories(Double calories) { this.calories = calories; }
    public Double getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(Double proteinGrams) { this.proteinGrams = proteinGrams; }
    public Double getCarbohydrateGrams() { return carbohydrateGrams; }
    public void setCarbohydrateGrams(Double carbohydrateGrams) { this.carbohydrateGrams = carbohydrateGrams; }
    public Double getFatGrams() { return fatGrams; }
    public void setFatGrams(Double fatGrams) { this.fatGrams = fatGrams; }

    public static FoodEntryForm from(FoodEntry entry) {
        FoodEntryForm form = new FoodEntryForm();
        form.setLogDate(entry.getLogDate());
        form.setMealType(entry.getMealType());
        form.setFoodName(entry.getFoodName());
        form.setServingQuantity(entry.getServingQuantity());
        form.setServingUnit("ml".equals(entry.getServingUnit()) ? "ml" : "g");
        form.setCalories(entry.getCalories());
        form.setProteinGrams(entry.getProteinGrams());
        form.setCarbohydrateGrams(entry.getCarbohydrateGrams());
        form.setFatGrams(entry.getFatGrams());
        return form;
    }
}
