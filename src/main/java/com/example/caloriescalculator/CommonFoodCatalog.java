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
                    .filter(food -> repository.findByFoodCode(food.getFoodCode()).isEmpty())
                    .forEach(repository::save);
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
        return new CommonFood(value[0], value[1], value[2], value[3], value[4],
                Double.valueOf(value[5]), Double.valueOf(value[6]), Double.valueOf(value[7]),
                Double.valueOf(value[8]), value[9], value[10]);
    }
}
