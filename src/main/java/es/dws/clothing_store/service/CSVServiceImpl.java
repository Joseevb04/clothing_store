package es.dws.clothing_store.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CSVServiceImpl implements CSVService {

    private final String DIRECTORY_PATH = "data";

    public <T> void writeObjectsToCsv(List<T> objects, String fileName) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("The objects list cannot be null or empty.");
        }

        try {
            Path filePath = Paths.get(DIRECTORY_PATH, fileName);

            Files.createDirectories(filePath.getParent());

            try (var writer = new FileWriter(filePath.toFile())) {
                Class<?> clazz = objects.get(0).getClass();
                Field[] fields = clazz.getDeclaredFields();
                String header = generateCsvHeader(fields);
                writer.write(header);

                objects.forEach(object -> {
                    try {
                        String row = generateCsvRow(object, fields);
                        writer.write(row);
                    } catch (Exception e) {
                        throw new RuntimeException("Error writing object to CSV row: " + e.getMessage(), e);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Error writing objects to CSV: " + e.getMessage(), e);
        }
    }

    /**
     * Reads a CSV file and maps its lines to a list of objects.
     *
     * @param fileName   the name of the CSV file to read
     * @param lineMapper a function to map each CSV line to an object
     * @param <T>        the type of object being read
     * @return a list of objects mapped from the CSV
     */
    public <T> List<T> readFromCsv(String fileName, Function<String, T> lineMapper) {
        try {
            Path filePath = Paths.get(DIRECTORY_PATH, fileName);

            if (!Files.exists(filePath)) {
                throw new RuntimeException("File not found: " + filePath);
            }

            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

            return lines.stream()
                    .skip(1) // Skip header row
                    .map(lineMapper)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data from CSV: " + fileName, e);
        }
    }

    @Override
    public void createCsvFile(String fileName) {
        try {
            Path filePath = Paths.get(DIRECTORY_PATH, fileName);

            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                log.info("CSV file created successfully at {}", filePath);
            } else {
                log.info("CSV file already exists at {}", filePath);
            }
        } catch (IOException e) {
            log.error("Error creating CSV file", e);
            throw new RuntimeException("Failed to create CSV file", e);
        }
    }

    private String generateCsvHeader(Field[] fields) {
        return Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.joining(",")) + System.lineSeparator();
    }

    private <T> String generateCsvRow(T object, Field[] fields) throws IllegalAccessException {
        return Arrays.stream(fields)
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return String.valueOf(field.get(object));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error accessing field value: " + field.getName(), e);
                    }
                })
                .collect(Collectors.joining(",")) + System.lineSeparator();
    }
}
