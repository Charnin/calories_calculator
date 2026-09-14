package com.example.caloriescalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonFoodCatalog implements ApplicationRunner {
    private static final String DATA_FILE = "data/common-foods.csv";
    private final CommonFoodRepository repository;

    public CommonFoodCatalog(CommonFoodRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(DATA_FILE).getInputStream(), StandardCharsets.UTF_8))) {
            reader.lines()
                    .filter(line -> !line.isBlank() && !line.startsWith("#") && !line.startsWith("foodCode;"))
                    .map(this::parse)
                    .forEach(food -> repository.findByFoodCode(food.getFoodCode())
                            .ifPresentOrElse(existing -> existing.updateFrom(food), () -> repository.save(food)));
        }
    }

    public List<CommonFood> list() {
        return repository.findAllByOrderByNameThAsc();
    }

    private CommonFood parse(String line) {
        String[] value = line.split(";", -1);
        if (value.length != 11) {
            throw new IllegalStateException("Invalid common food row: " + line);
        }
        Serving serving = servingFor(value[0], value[3], value[4]);
        return new CommonFood(value[0], value[1], value[2], value[3], value[4],
                Double.valueOf(value[5]), Double.valueOf(value[6]), Double.valueOf(value[7]),
                Double.valueOf(value[8]), serving.size(), serving.labelTh(), serving.labelEn(),
                value[9], value[10]);
    }

    private Serving servingFor(String foodCode, String category, String baseUnit) {
        if (foodCode.startsWith("EST")) {
            int number = Integer.parseInt(foodCode.substring(3));
            if (number >= 4 && number <= 18) return new Serving(300, "1 จาน", "1 plate");
            if (number >= 35 && number <= 39) return new Serving(400, "1 ชาม", "1 bowl");
            if (number >= 43) return new Serving(250, "1 แก้ว", "1 glass");
            if (number >= 40) return new Serving(100, "1 ที่", "1 serving");
            return new Serving(150, "1 ที่", "1 serving");
        }
        return switch (category) {
            case "อาหารจานเดียว" -> new Serving(300, "1 จาน", "1 plate");
            case "ก๋วยเตี๋ยว" -> new Serving(400, "1 ชาม", "1 bowl");
            case "กับข้าว" -> new Serving(150, "1 ถ้วยเล็ก", "1 small bowl");
            case "ไข่" -> new Serving(50, "1 ฟอง", "1 egg");
            case "เครื่องดื่ม" -> new Serving(250, "1 แก้ว", "1 glass");
            case "ขนม" -> new Serving(100, "1 ที่", "1 serving");
            case "ผลไม้" -> fruitServing(foodCode);
            default -> new Serving(100, "ml".equals(baseUnit) ? "100 มิลลิลิตร" : "100 กรัม",
                    "ml".equals(baseUnit) ? "100 milliliters" : "100 grams");
        };
    }

    private Serving fruitServing(String foodCode) {
        return switch (foodCode) {
            case "THE3" -> new Serving(80, "1 ผลเล็ก", "1 small fruit");
            case "THE8" -> new Serving(120, "1 ผล", "1 fruit");
            case "THE115" -> new Serving(100, "1 ผล", "1 fruit");
            default -> new Serving(150, "1 จานเล็ก", "1 small plate");
        };
    }

    private record Serving(double size, String labelTh, String labelEn) { }
}
